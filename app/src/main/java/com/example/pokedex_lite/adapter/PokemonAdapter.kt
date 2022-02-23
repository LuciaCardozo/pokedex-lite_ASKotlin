package com.example.pokedex_lite.adapter

import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.pokedex_lite.R
import androidx.recyclerview.widget.RecyclerView
import io.swagger.client.models.Pokemon

class PokemonAdapter(private val listPokemon:List<Pokemon>,private val onClickListener: (Pokemon)->Unit):RecyclerView.Adapter<PokemonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PokemonViewHolder(layoutInflater.inflate(R.layout.item_pokemon, parent,false))
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val item = listPokemon[position]
        holder.render(item,onClickListener)
    }

    override fun getItemCount(): Int {
        return listPokemon.size
    }

}