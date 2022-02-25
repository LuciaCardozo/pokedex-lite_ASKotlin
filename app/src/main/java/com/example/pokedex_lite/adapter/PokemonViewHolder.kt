package com.example.pokedex_lite.adapter

import android.view.View
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokedex_lite.databinding.ItemPokemonBinding
import com.example.pokedex_lite.model.ImagePokemonProvider
import io.swagger.client.models.Pokemon

class PokemonViewHolder(view:View):RecyclerView.ViewHolder(view){
    private val binding = ItemPokemonBinding.bind(view)

    fun render(pokemon:Pokemon,onClickListener: (Pokemon)->Unit){
        //PHOTO HARDCODEADA
        var imagePokemon:String = ImagePokemonProvider.addImage(pokemon.image.toString())
        Glide.with(binding.ivPokemon.context).load(imagePokemon).into(binding.ivPokemon)
        binding.tvPokemonName.text = pokemon.name
        var level = pokemon.lvl.toString()
        binding.tvPokemonLvl.text = "Level: $level"
        val types:MutableList<String> = mutableListOf()
        for(i in pokemon.type!!){
            types.add(i)
        }
        binding.tvTypes.text = "Tipos: ${types.toString().replace("[","").replace("]","")}"
        itemView.setOnClickListener{
            onClickListener(pokemon)
        }
    }

}