package pokemonsimulation.view

import pokemonsimulation.MainApp
import pokemonsimulation.model.{Battle, Pokemon}
import scalafx.event.ActionEvent
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, Button, Label, ProgressBar}
import scalafx.scene.image.ImageView
import scalafx.scene.media.MediaPlayer
import scalafxml.core.macros.sfxml

@sfxml
class FightingScreenController(
                              private var pokemonChosenImage: ImageView,
                              private var pokemonChosenHealth: ProgressBar,
                              private var pokemonChosenHealthText: Label,
                              private var pokemonChosenName: Label,
                              private var ability1: Button,
                              private var ability2: Button,
                              private var ability3: Button,
                              private var pokemonChosenOpponentImage: ImageView,
                              private var pokemonChosenOpponentHealth: ProgressBar,
                              private var pokemonChosenOpponentHealthText: Label,
                              private var pokemonChosenOpponentName: Label,
                              private var escapeButton: Button
                              ) extends BaseController {
  private var chosenPokemon: Pokemon = _
  private var opponentPokemon: Pokemon = _
  private var mediaPlayer: MediaPlayer = _
  private var buttonClickMediaPlayer: MediaPlayer = _
  private var soundEffectMediaPlayer: MediaPlayer = _
  private var battle: Battle = _

  def showFightingScreen(chosenPokemon: Pokemon, opponentPokemon: Pokemon): Unit = {
    mediaPlayer = playBGM("../backgroundmusic/fightingBGM.mp3", volume = 0.3)
    this.chosenPokemon = chosenPokemon
    this.opponentPokemon = opponentPokemon
    battle = new Battle(chosenPokemon, opponentPokemon)

    updatePokemonInfo()
  }

  def updatePokemonInfo(): Unit = {
    if (chosenPokemon != null && opponentPokemon != null) {
      pokemonChosenImage.setImage(chosenPokemon.getImage)
      pokemonChosenImage.fitWidth = 200
      pokemonChosenImage.fitHeight = 200
      pokemonChosenHealth.progress = chosenPokemon.health / 100.0
      pokemonChosenHealthText.text = chosenPokemon.health.toString + "/100.0"
      pokemonChosenName.text = chosenPokemon.name
      ability1.text = chosenPokemon.abilities(0).getClass.getSimpleName.stripSuffix("$")
      ability2.text = chosenPokemon.abilities(1).getClass.getSimpleName.stripSuffix("$")
      ability3.text = chosenPokemon.abilities(2).getClass.getSimpleName.stripSuffix("$")
      pokemonChosenOpponentImage.setImage(opponentPokemon.getImage)
      pokemonChosenOpponentImage.fitHeight = 200
      pokemonChosenOpponentImage.fitWidth = 200
      pokemonChosenOpponentHealth.progress = opponentPokemon.health / 100.0
      pokemonChosenOpponentHealthText.text = opponentPokemon.health.toString + "/100.0"
      pokemonChosenOpponentName.text = opponentPokemon.name
    }
  }

  private def checkHealthStatus(): Unit = {
    if (chosenPokemon.health == 0) {
      showLoseAlert()
    } else if (opponentPokemon.health == 0) {
      showWinAlert()
    }
  }

  private def showLoseAlert(): Unit = {
    mediaPlayer.stop()
    soundEffectMediaPlayer = playSoundEffect("../soundeffect/losingSoundEffect.mp3", volume = 0.3)
    val alert = new Alert(AlertType.Information)
    alert.title = "Game Over"
    alert.headerText = "Better Luck Next Time!"
    alert.showAndWait()
    soundEffectMediaPlayer.stop()
    chosenPokemon.happiness -= 20
    chosenPokemon.drowsiness += 20
    chosenPokemon.satiety -= 20
    MainApp.showMainGameScreen(chosenPokemon)
  }

  private def showWinAlert(): Unit = {
    mediaPlayer.stop()
    soundEffectMediaPlayer = playSoundEffect("../soundeffect/victorySoundEffect.mp3", volume = 0.3)
    val alert = new Alert(AlertType.Information)
    alert.title = "Congratulations!"
    alert.headerText = "You Have Win the Game!"
    alert.showAndWait()
    soundEffectMediaPlayer.stop()
    chosenPokemon.happiness += 20
    chosenPokemon.drowsiness += 20
    chosenPokemon.satiety -= 20
    chosenPokemon.experience += 100
    MainApp.showMainGameScreen(chosenPokemon)
  }

  def ability1(action: ActionEvent): Unit = {
    if (chosenPokemon.health > 0) {
      battle.playerMove(0)
      updateAfterMove()
      opponentTurn()
      checkHealthStatus()
    }
  }

  def ability2(action: ActionEvent): Unit = {
    if (chosenPokemon.health > 0) {
      battle.playerMove(1)
      updateAfterMove()
      opponentTurn()
      checkHealthStatus()
    }
  }

  def ability3(action: ActionEvent): Unit = {
    if (chosenPokemon.health > 0) {
      battle.playerMove(2)
      updateAfterMove()
      opponentTurn()
      checkHealthStatus()
    }
  }

  private def updateAfterMove(): Unit = {
    chosenPokemon.health = Math.max(chosenPokemon.health, 0)
    opponentPokemon.health = Math.max(opponentPokemon.health, 0)

    pokemonChosenHealth.progress = chosenPokemon.health / 100.0
    pokemonChosenHealthText.text = chosenPokemon.health.toString + "/100.0"
  }

  private def opponentTurn(): Unit = {
    battle.opponentTurn()
    updateAfterMove()
    pokemonChosenOpponentHealth.progress = opponentPokemon.health / 100.0
    pokemonChosenOpponentHealthText.text = opponentPokemon.health.toString + "/100.0"
  }

  def escape(action: ActionEvent): Unit = {
    if (battle.attemptEscape()) {
      soundEffectMediaPlayer = playSoundEffect("../soundeffect/runAwaySoundEffect.mp3", volume = 0.3)
      mediaPlayer.stop()
      chosenPokemon.happiness -= 10
      chosenPokemon.drowsiness += 5
      chosenPokemon.satiety -= 5
      MainApp.showMainGameScreen(chosenPokemon)
    } else {
      val alert = new Alert(AlertType.Warning)
      alert.title = "Escape Failed"
      alert.headerText = "Cannot Escape Now"
      alert.showAndWait()
      updateEscapeButtonStatus(true)
      opponentTurn()
    }
  }

  private def updateEscapeButtonStatus(enable: Boolean): Unit = {
    escapeButton.disable = !enable
  }
}