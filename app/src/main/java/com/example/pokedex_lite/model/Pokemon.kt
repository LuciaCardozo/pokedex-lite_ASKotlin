package com.example.pokedex_lite.model

class Pokemon:ArrayList<PokemonCollection>()
    data class PokemonCollection(
        val id: Int? = null,
        val name: String? = null,
        val lvl: Int? = null,
        val evolutionId: Int? = null,
        val abilities: Array<Abilities>? = null,
        val type: Array<String>? = null,
        val image: String? = null
    )
    data class Abilities(
        val name:String,
        val description:String
    )