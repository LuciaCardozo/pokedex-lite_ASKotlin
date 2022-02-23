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
        binding.tvPokemonLvl.text = pokemon.lvl.toString()
        val arrayAdapter:ArrayAdapter<String>
        val types:MutableList<String> = mutableListOf()
        val listType = binding.lvTypes
        for(i in pokemon.type!!){
            types.add(i)
        }

        arrayAdapter = ArrayAdapter(listType.context,android.R.layout.simple_list_item_1,types)
        listType.adapter = arrayAdapter
        itemView.setOnClickListener{
            onClickListener(pokemon)
        }
    }

}