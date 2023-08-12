package pokemonsimulation.util

import pokemonsimulation.model.{Bulbasaur, Charmander, Pokemon, Squirtle}

import scala.collection.mutable

object PokemonDatabase {
  private val pokemonData: mutable.Map[String, Pokemon] = mutable.Map.empty

  def savePokemon(pokemon: Pokemon): Unit = {
    pokemonData += (pokemon.name -> pokemon)
  }

  def getPokemon(name: String): Option[Pokemon] = {
    pokemonData.get(name)
  }

  def getAllPokemon(): List[Pokemon] = {
    pokemonData.values.toList
  }

  def initializeDatabase(): Unit = {
    val bulbasaur = new Bulbasaur
    savePokemon(bulbasaur)

    val charmander = new Charmander
    savePokemon(charmander)

    val squirtle = new Squirtle
    savePokemon(squirtle)
  }

  initializeDatabase()
}
