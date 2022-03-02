package com.example.pokedex_lite.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex_lite.R
import com.example.pokedex_lite.databinding.ItemPokemonBinding
import io.swagger.client.models.PokemonAbilities

class PokemonAbilityAdapter(private val listPokemonAbilities:List<PokemonAbilities>)
    :RecyclerView.Adapter<PokemonAbilitiesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonAbilitiesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PokemonAbilitiesViewHolder(layoutInflater.inflate(R.layout.item_pokemon_ability, parent,false))
    }

    override fun onBindViewHolder(holder: PokemonAbilitiesViewHolder, position: Int) {
        val item = listPokemonAbilities[position]
        holder.render(item)
    }

    override fun getItemCount(): Int {
        return listPokemonAbilities.size
    }


}