package pokemonsimulation.view

import javafx.fxml.FXML
import pokemonsimulation.MainApp
import scalafx.event.ActionEvent
import scalafx.scene.media.MediaPlayer
import scalafxml.core.macros.sfxml

@sfxml
class SplashScreenController() extends BaseController {
  private var mediaPlayer: MediaPlayer = _
  private var buttonClickMediaPlayer: MediaPlayer = _

  initialize()

  private def initialize(): Unit = {
    // Background Music
    mediaPlayer = playBGM("../backgroundmusic/splashScreenBGM.mp3", volume = 0.3)
  }

  // Button Sound Effect
  def startGame(action: ActionEvent): Unit = {
    // Stop the BGM and Play the button click sound effect
    stopMediaPlayer(mediaPlayer)
    buttonClickMediaPlayer = playSoundEffect("../soundeffect/selectSoundEffect.mp3", volume = 0.3)
    // Bring to the next Page
    MainApp.showUserManual()
  }
}
