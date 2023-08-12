package pokemonsimulation.view

import pokemonsimulation.MainApp
import pokemonsimulation.model.Pokemon
import pokemonsimulation.util.PokemonDatabase
import scalafx.event.ActionEvent
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, Label, ProgressBar}
import scalafx.scene.image.ImageView
import scalafx.scene.media.MediaPlayer
import scalafxml.core.macros.sfxml

@sfxml
class MainGameController(
                          private var pokemonLevelLabel: Label,
                          private var experienceBar: ProgressBar,
                          private var pokemonImage: ImageView,
                          private var happinessBar: ProgressBar,
                          private var happinessLabel: Label,
                          private var drowsinessBar: ProgressBar,
                          private var drowsinessLabel: Label,
                          private var satietyBar: ProgressBar,
                          private var satietyLabel: Label
                        ) extends BaseController {

  private var chosenPokemon: Pokemon = _
  private var mediaPlayer: MediaPlayer = _
  private var buttonClickMediaPlayer: MediaPlayer = _
  private var soundEffectMediaPlayer: MediaPlayer = _

  def showMainGameScreen(pokemon: Pokemon): Unit = {
    mediaPlayer = playBGM("../backgroundmusic/mainGameLofiBGM.mp3", volume = 0.3)
    chosenPokemon = pokemon
    updatePokemonInfo()
  }

  def updatePokemonInfo(): Unit = {
    if (chosenPokemon != null) {
      updateExperienceAndLevel()
      updateStatusBars()
      updateHappinessBarColor()
      updateDrowsinessBarColor()
      updateSatietyBarColor()
      updatePokemonImage()
    }
  }

  private def updateExperienceAndLevel(): Unit = {
    pokemonLevelLabel.text = chosenPokemon.level.toString
    experienceBar.progress = chosenPokemon.experience / 100.0

    if (chosenPokemon.experience >= 100) {
      soundEffectMediaPlayer = playSoundEffect("../soundeffect/levelUpSoundEffect.mp3", volume = 0.1)
      chosenPokemon.level += 1
      chosenPokemon.experience -= 100
      updatePokemonInfo()
    }
  }

  private def updateStatusBars(): Unit = {
    chosenPokemon.happiness = chosenPokemon.happiness.max(0).min(100)
    chosenPokemon.drowsiness = chosenPokemon.drowsiness.max(0).min(100)
    chosenPokemon.satiety = chosenPokemon.satiety.max(0).min(100)

    happinessBar.progress = chosenPokemon.happiness / 100.0
    happinessLabel.text = s"Happiness: ${chosenPokemon.happiness}%"

    drowsinessBar.progress = chosenPokemon.drowsiness / 100.0
    drowsinessLabel.text = s"Drowsiness: ${chosenPokemon.drowsiness}%"

    satietyBar.progress = chosenPokemon.satiety / 100.0
    satietyLabel.text = s"Satiety: ${chosenPokemon.satiety}%"
  }

  private def updateHappinessBarColor(): Unit = {
    if (chosenPokemon.happiness < 20) {
      happinessBar.styleClass.setAll("progress-bar", "bar-red")
    } else if (chosenPokemon.happiness < 40) {
      happinessBar.styleClass.setAll("progress-bar", "bar-orange")
    } else if (chosenPokemon.happiness < 70) {
      happinessBar.styleClass.setAll("progress-bar", "bar-yellow")
    } else {
      happinessBar.styleClass.setAll("progress-bar")
    }
  }

  private def updateDrowsinessBarColor(): Unit = {
    if (chosenPokemon.drowsiness > 80) {
      drowsinessBar.styleClass.setAll("progress-bar", "bar-red")
    } else if (chosenPokemon.drowsiness > 60) {
      drowsinessBar.styleClass.setAll("progress-bar", "bar-orange")
    } else if (chosenPokemon.drowsiness > 30) {
      drowsinessBar.styleClass.setAll("progress-bar", "bar-yellow")
    } else {
      drowsinessBar.styleClass.setAll("progress-bar")
    }
  }

  private def updateSatietyBarColor(): Unit = {
    if (chosenPokemon.satiety < 20) {
      satietyBar.styleClass.setAll("progress-bar", "bar-red")
    } else if (chosenPokemon.satiety < 40) {
      satietyBar.styleClass.setAll("progress-bar", "bar-orange")
    } else if (chosenPokemon.satiety < 70) {
      satietyBar.styleClass.setAll("progress-bar", "bar-yellow")
    } else {
      satietyBar.styleClass.setAll("progress-bar")
    }
  }

  private def updatePokemonImage(): Unit = {
    pokemonImage.setImage(chosenPokemon.getImage)
    pokemonImage.fitHeight = 350
    pokemonImage.fitWidth = 350
  }

  def feedPokemon(actionEvent: ActionEvent): Unit = {
    buttonClickMediaPlayer = playSoundEffect("../soundeffect/selectSoundEffect.mp3", volume = 0.3)
    chosenPokemon.satiety += 5
    updatePokemonInfo()
  }

  def restPokemon(): Unit = {
    buttonClickMediaPlayer = playSoundEffect("../soundeffect/selectSoundEffect.mp3", volume = 0.3)
    chosenPokemon.drowsiness -= 5
    updatePokemonInfo()
  }

  def playPokemon(): Unit = {
    buttonClickMediaPlayer = playSoundEffect("../soundeffect/selectSoundEffect.mp3", volume = 0.3)
    chosenPokemon.happiness += 5
    chosenPokemon.drowsiness += 5
    updatePokemonInfo()
  }

  def fightPokemon(): Unit = {
    if (chosenPokemon.happiness < 50 || chosenPokemon.drowsiness > 50 || chosenPokemon.satiety < 50) {
      val alert = new Alert(AlertType.Warning)
      alert.title = "Cannot Fight"
      alert.headerText = "Your Pokemon is not in the right condition to fight"
      alert.contentText = "Ensure that happiness, drowsiness, and satiety are all at healthy levels before fighting."
      alert.showAndWait()
    } else {
      val availableOpponents = PokemonDatabase.getAllPokemon().filter(_ != chosenPokemon)
      val randomOpponentIndex = scala.util.Random.nextInt(availableOpponents.length)
      val chosenOpponent = availableOpponents(randomOpponentIndex)
      chosenPokemon.resetHealth()
      chosenOpponent.resetHealth()
      mediaPlayer.stop()
      MainApp.showFightingScreen(chosenPokemon, chosenOpponent)
    }
  }

  def getBack(action: ActionEvent): Unit = {
    stopMediaPlayer(mediaPlayer)
    buttonClickMediaPlayer = playSoundEffect("../soundeffect/selectSoundEffect.mp3", volume = 0.3)
    MainApp.showPokemonSelectionScreen()
  }
}
