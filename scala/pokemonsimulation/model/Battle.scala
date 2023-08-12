package pokemonsimulation.model

import scala.util.Random

class Battle(playerPokemon: Pokemon, opponentPokemon: Pokemon) {
  private var escapeAttempted: Boolean = false

  def playerMove(abilityIndex: Int): Unit = {
    val ability = playerPokemon.abilities(abilityIndex)
    playerPokemon.attack(ability, opponentPokemon)
  }

  def opponentTurn(): Unit = {
    val abilityIndex = Random.nextInt(3)
    val ability = opponentPokemon.abilities(abilityIndex)
    opponentPokemon.attack(ability, playerPokemon)
  }

  def attemptEscape(): Boolean = {
    if (!escapeAttempted) {
      val escapeChance = Random.nextDouble()
      if (escapeChance < 0.5) {
        escapeAttempted = true
        false
      } else {
        true
      }
    } else {
      false
    }
  }
}
