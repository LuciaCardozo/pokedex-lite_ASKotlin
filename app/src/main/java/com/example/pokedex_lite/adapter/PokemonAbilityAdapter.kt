package com.example.pokedex_lite.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.pokedex_lite.R
import com.example.pokedex_lite.databinding.ItemPokemonBinding
import io.swagger.client.models.PokemonAbilities

class PokemonAbilityAdapter(private val pContext:Context,private val listPokemonAbilities:List<PokemonAbilities>)
    :ArrayAdapter<PokemonAbilities>(pContext,0,listPokemonAbilities) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var pLayout = LayoutInflater.from(pContext).inflate(R.layout.item_pokemon_ability,parent,false)
        val pokeAbility = listPokemonAbilities[position]

        return pLayout
    }
}