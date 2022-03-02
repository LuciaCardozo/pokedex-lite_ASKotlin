package com.example.pokedex_lite.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex_lite.databinding.ItemPokemonAbilityBinding
import io.swagger.client.models.PokemonAbilities

class PokemonAbilitiesViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private var binding = ItemPokemonAbilityBinding.bind(view)

    fun render(pokemon: PokemonAbilities){
        //PHOTO HARDCODEADA
        var name:String = pokemon.name.toString()
        binding.nameAbility.text = "Name: $name"
        var description:String = pokemon.description.toString()
        binding.description.text = "Description: $description"
    }
}