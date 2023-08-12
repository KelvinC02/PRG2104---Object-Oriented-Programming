package pokemonsimulation.model

import pokemonsimulation.model.ElementalDamageMatrix.elementalMatrix
import pokemonsimulation.model.PokemonAbility.{AquaJet, BulletSeed, Ember, FireSpin, FlameCharge, RazorLeaf, VineWhip, WaterGun, WaterPulse}
import scalafx.scene.image.Image

abstract class Pokemon {
  val imagePath: String
  val name: String
  var level: Int
  var experience: Int
  var health: Double
  val attackDamage: Int
  val elementalAttributes: String
  var happiness: Int
  var drowsiness: Int
  var satiety: Int
  var abilities: List[Ability]

  def getImage: Image = new Image(getClass.getResourceAsStream(imagePath))

  def attack(selectedAbility: Ability, opponent: Pokemon): Unit = {
    val damageMultiplier = elementalMatrix(elementalAttributes)(opponent.elementalAttributes)
    val calculatedDamage = (selectedAbility.damage * damageMultiplier).toInt
    opponent.health -= calculatedDamage
  }

  def resetHealth(): Unit = {
    health = 100.0
  }
}

trait Grass {
  val elementalAttributes: String = "Grass"
}

trait Fire {
  val elementalAttributes: String = "Fire"
}

trait Water {
  val elementalAttributes: String = "Water"
}


abstract class Ability {
  val damage: Int
}

object PokemonAbility {
  //Bulbasaur&IvySaur
  object BulletSeed extends Ability() {
    override val damage: Int = 5
  }

  object VineWhip extends Ability() {
    override val damage: Int = 9
  }

  object RazorLeaf extends Ability() {
    override val damage: Int = 11
  }

  //Charmander&Charmeleon
  object FireSpin extends Ability() {
    override val damage: Int = 7
  }

  object Ember extends Ability() {
    override val damage: Int = 8
  }

  object FlameCharge extends Ability() {
    override val damage: Int = 10
  }

  //Squirtle&Wartotle
  object WaterGun extends Ability() {
    override val damage: Int = 8
  }

  object AquaJet extends Ability() {
    override val damage: Int = 8
  }

  object WaterPulse extends Ability() {
    override val damage: Int = 12
  }
}

class Bulbasaur extends Pokemon with Grass {
  val imagePath = "../image/bulbasaur.gif"
  val name = "Bulbasaur"
  var level = 1
  var experience = 0
  var health = 100.0
  val attackDamage = 40
  var happiness = 100
  var drowsiness = 0
  var satiety = 100
  var abilities: List[Ability] = List(BulletSeed, VineWhip, RazorLeaf)
}

class Charmander extends Pokemon with Fire {
  val imagePath = "../image/charmander.gif"
  val name = "Charmander"
  var level = 1
  var experience = 0
  var health = 100.0
  val attackDamage = 50
  var happiness = 100
  var drowsiness = 0
  var satiety = 100
  var abilities: List[Ability] = List(FireSpin, Ember, FlameCharge)
}

class Squirtle extends Pokemon with Water {
  val imagePath = "../image/squirtle.gif"
  val name = "Squirtle"
  var level = 1
  var experience = 0
  var health = 100.0
  val attackDamage = 45
  var happiness = 100
  var drowsiness = 0
  var satiety = 100
  var abilities: List[Ability] = List(WaterGun, AquaJet, WaterPulse)
}

object ElementalDamageMatrix {
  val elementalMatrix: Map[String, Map[String, Double]] = Map(
    "Grass" -> Map("Fire" -> 0.9, "Water" -> 1.1),
    "Fire" -> Map("Water" -> 0.9, "Grass" -> 1.1),
    "Water" -> Map("Grass" -> 0.9, "Fire" -> 1.1)
  )
}