package pokemonsimulation.view

import scalafx.scene.media.{Media, MediaPlayer}

abstract class BaseController {
  protected def playBGM(bgmURL: String, volume: Double): MediaPlayer = {
    val bgmMedia = new Media(getClass.getResource(bgmURL).toString)
    val mediaPlayer = new MediaPlayer(bgmMedia)
    mediaPlayer.play()
    mediaPlayer.volume = volume

    // Loop the media manually when it reaches the end
    mediaPlayer.onEndOfMedia = () => mediaPlayer.seek(scalafx.util.Duration.Zero)

    mediaPlayer
  }

  protected def playSoundEffect(soundEffectURL: String, volume: Double): MediaPlayer = {
    val soundEffectMedia = new Media(getClass.getResource(soundEffectURL).toString)
    val mediaPlayer = new MediaPlayer(soundEffectMedia)
    mediaPlayer.volume = volume
    mediaPlayer.play()
    mediaPlayer
  }

  protected def stopMediaPlayer(mediaPlayer: MediaPlayer): Unit = {
    if (mediaPlayer != null) {
      mediaPlayer.stop()
    }
  }
}
