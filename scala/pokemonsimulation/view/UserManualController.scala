package pokemonsimulation.view

import pokemonsimulation.MainApp
import scalafx.event.ActionEvent
import scalafx.scene.media.MediaPlayer
import scalafxml.core.macros.sfxml

@sfxml
class UserManualController() extends BaseController {
  private var mediaPlayer: MediaPlayer = _
  private var buttonClickMediaPlayer: MediaPlayer = _

  initialize()

  private def initialize(): Unit = {
    // Background Music
    mediaPlayer = playBGM("../backgroundmusic/userManualBGM.mp3", volume = 0.3)
  }

  def getBack(action: ActionEvent): Unit = {
    stopMediaPlayer(mediaPlayer)
    buttonClickMediaPlayer = playSoundEffect("../soundeffect/selectSoundEffect.mp3", volume = 0.3)
    MainApp.showSplashScreen()
  }

  def goNext(actionEvent: ActionEvent): Unit = {
    stopMediaPlayer(mediaPlayer)
    buttonClickMediaPlayer = playSoundEffect("../soundeffect/selectSoundEffect.mp3", volume = 0.3)
    MainApp.showPokemonSelectionScreen()
  }
}
