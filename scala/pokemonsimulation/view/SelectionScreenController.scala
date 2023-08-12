package pokemonsimulation.view

import pokemonsimulation.MainApp
import pokemonsimulation.MainApp.stage
import pokemonsimulation.model.{Bulbasaur, Charmander, Pokemon, Squirtle}
import pokemonsimulation.util.PokemonDatabase
import scalafx.event.ActionEvent
import scalafx.scene.control.{Alert, ButtonType, Label}
import scalafx.scene.media.MediaPlayer
import scalafxml.core.macros.sfxml

@sfxml
class SelectionScreenController extends BaseController {
  private var mediaPlayer: MediaPlayer = _
  private var buttonClickMediaPlayer: MediaPlayer = _
  private var soundEffectMediaPlayer: MediaPlayer = _

  initialize()

  private def initialize(): Unit = {
    // Background Music
    mediaPlayer = playBGM("../backgroundmusic/selectPokemonBGM.mp3", volume = 0.3)
  }

  private def showConfirmationDialog(message: String): Boolean = {
    val confirmationAlert = new Alert(Alert.AlertType.Confirmation) {
      initOwner(stage)
      title = "Confirmation"
      headerText = "Please confirm"
      contentText = message
    }

    val result = confirmationAlert.showAndWait()
    result match {
      case Some(ButtonType.OK) => true
      case _ => false
    }
  }

  // Common method to handle Pokemon selection
  private def handlePokemonSelection(pokemonName: String, crySoundEffect: String): Unit = {
    soundEffectMediaPlayer = playSoundEffect(crySoundEffect, volume = 0.3)
    mediaPlayer.pause()

    val confirmationMessage = s"Are you sure you want to choose $pokemonName?"
    val confirmed = showConfirmationDialog(confirmationMessage)

    if (confirmed) {
      val chosenPokemon = createPokemonInstance(pokemonName)
      PokemonDatabase.savePokemon(chosenPokemon)
      MainApp.showMainGameScreen(chosenPokemon)
    } else {
      mediaPlayer.play()
    }
  }

  private def createPokemonInstance(pokemonName: String): Pokemon = {
    val chosenPokemon = pokemonName.toLowerCase() match {
      case "bulbasaur" => new Bulbasaur
      case "charmander" => new Charmander
      case "squirtle" => new Squirtle
      case _ => throw new IllegalArgumentException("Invalid Pokemon Name")
    }
    println("Chosen Pokemon instance: " + chosenPokemon) // Add this line to check the instance of chosenPokemon
    chosenPokemon
  }

  private def handlePokemonDetails(pokemonName: String): Unit = {
    val chosenPokemon = createPokemonInstance(pokemonName)
    MainApp.showDetailsScreen(chosenPokemon)
    mediaPlayer.pause()
  }

  def selectBulbasaur(action: ActionEvent): Unit = {
    handlePokemonSelection("Bulbasaur", "../soundeffect/bulbasaurCry.mp3")
  }

  def detailsBulbasaur(action: ActionEvent): Unit = {
    handlePokemonDetails("Bulbasaur")
  }

  def selectCharmander(action: ActionEvent): Unit = {
    handlePokemonSelection("Charmander", "../soundeffect/charmanderCry.mp3")
  }

  def detailsCharmander(action: ActionEvent): Unit = {
    handlePokemonDetails("Charmander")
  }

  def selectSquirtle(action: ActionEvent): Unit = {
    handlePokemonSelection("Squirtle", "../soundeffect/squirtleCry.mp3")
  }

  def detailsSquirtle(action: ActionEvent): Unit = {
    handlePokemonDetails("Squirtle")
  }

  def getBack(action: ActionEvent): Unit = {
    stopMediaPlayer(mediaPlayer)
    buttonClickMediaPlayer = playSoundEffect("../soundeffect/selectSoundEffect.mp3", volume = 0.3)
    MainApp.showUserManual()
  }
}
