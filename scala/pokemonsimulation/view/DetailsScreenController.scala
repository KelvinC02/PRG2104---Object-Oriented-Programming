package pokemonsimulation.view

import pokemonsimulation.MainApp
import pokemonsimulation.model.Pokemon
import scalafx.event.ActionEvent
import scalafx.scene.control.Label
import scalafx.scene.image.ImageView
import scalafx.scene.media.MediaPlayer
import scalafxml.core.macros.sfxml

@sfxml
class DetailsScreenController(
                             private var pokemonImage: ImageView,
                             private var pokemonName: Label,
                             private var pokemonEA: Label,
                             private var pokemonAbilities1: Label,
                             private var pokemonAbilities2: Label,
                             private var pokemonAbilities3: Label
                             ) extends BaseController {

  private var chosenPokemon: Pokemon = _
  private var mediaPlayer: MediaPlayer = _
  private var buttonClickMediaPlayer: MediaPlayer = _
  private var soundEffectMediaPlayer: MediaPlayer = _

  def showDetailsScreen(pokemon: Pokemon): Unit = {
    mediaPlayer = playBGM("../backgroundmusic/detailsScreenBGM.mp3", volume = 0.3)
    soundEffectMediaPlayer = playSoundEffect("../soundeffect/pokedexSoundEffect2.mp3", volume = 0.3)
    chosenPokemon = pokemon
    showPokemonInfo()
  }

  private def showPokemonInfo(): Unit = {
    if (chosenPokemon != null) {
      pokemonImage.setImage(chosenPokemon.getImage)
      pokemonImage.fitHeight = 350
      pokemonImage.fitWidth = 350
      pokemonName.text = s"${chosenPokemon.name}"
      pokemonEA.text = s"${chosenPokemon.elementalAttributes}"
      pokemonAbilities1.text = chosenPokemon.abilities(0).getClass.getSimpleName.stripSuffix("$")
      pokemonAbilities2.text = chosenPokemon.abilities(1).getClass.getSimpleName.stripSuffix("$")
      pokemonAbilities3.text = chosenPokemon.abilities(2).getClass.getSimpleName.stripSuffix("$")
    }
  }

  def getBack(action: ActionEvent): Unit = {
    mediaPlayer.stop()
    soundEffectMediaPlayer.stop()
    buttonClickMediaPlayer = playSoundEffect("../soundeffect/selectSoundEffect.mp3", volume = 0.3)
    MainApp.showPokemonSelectionScreen()
  }
}
