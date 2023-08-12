package pokemonsimulation

import scalafxml.core.{FXMLLoader, NoDependencyResolver}
import javafx.{scene => jfxs}
import pokemonsimulation.model.Pokemon
import pokemonsimulation.view.{DetailsScreenController, FightingScreenController, MainGameController}
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene.image.Image

object MainApp extends JFXApp {
  val rootResource = getClass.getResource("view/RootLayout.fxml")
  val loader = new FXMLLoader(rootResource, NoDependencyResolver)
  loader.load()
  val roots = loader.getRoot[jfxs.layout.BorderPane]

  stage = new PrimaryStage() {
    title = "Pokemon Simulation"
    icons += new Image(getClass.getResource("image/logo.png").toURI.toString)
    resizable = false
    scene = new Scene() {
      root = roots
    }
  }

  showSplashScreen()

  def showSplashScreen() = {
    val resource = getClass.getResource("view/SplashScreen.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load()
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  }

  def showUserManual(): Unit = {
    val resource = getClass.getResource("view/UserManual.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load()
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  }

  def showPokemonSelectionScreen(): Unit = {
    val resource = getClass.getResource("view/SelectionScreen.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load()
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  }

  def showMainGameScreen(chosenPokemon: Pokemon): Unit = {
    val resource = getClass.getResource("view/MainGame.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    val mainGameController = new MainGameController(NoDependencyResolver)
    loader.setController(mainGameController)
    loader.load()
    mainGameController.showMainGameScreen(chosenPokemon)
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)

    println("Chosen Pokemon in MainApp: " + chosenPokemon)
  }

  def showDetailsScreen(chosenPokemon: Pokemon): Unit = {
    val resource = getClass.getResource("view/DetailsScreen.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    val detailsScreenController = new DetailsScreenController(NoDependencyResolver)
    loader.setController(detailsScreenController)
    loader.load()
    detailsScreenController.showDetailsScreen(chosenPokemon)
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)

    println("Details Pokemon in MainApp: " + chosenPokemon)
  }

  def showFightingScreen(chosenPokemon: Pokemon, opponentPokemon: Pokemon): Unit = {
    val resource = getClass.getResource("view/FightingScreen.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    val fightingScreenController = new FightingScreenController(NoDependencyResolver)
    loader.setController(fightingScreenController)
    loader.load()
    fightingScreenController.showFightingScreen(chosenPokemon, opponentPokemon)
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)

    println("Fighting Pokemon in MainApp: " + chosenPokemon + opponentPokemon)
  }
}
