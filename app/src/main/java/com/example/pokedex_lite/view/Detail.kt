package com.example.pokedex_lite.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.pokedex_lite.databinding.ActivityDetailBinding
import com.example.pokedex_lite.model.ImagePokemonProvider
import com.example.pokedex_lite.model.StorageApplication.Companion.prefs
import io.swagger.client.models.Pokemon

class Detail : AppCompatActivity() {
    private lateinit var binding:ActivityDetailBinding
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pokemon:Pokemon = intent.getSerializableExtra("pokemon") as Pokemon
        binding.tvPokeName.text = pokemon.name
        binding.tvPokeLvl.text = "Lvl: ${pokemon.lvl}"
        Glide.with(binding.ivPoke.context).load(ImagePokemonProvider.addImage(pokemon.image.toString())).into(binding.ivPoke)
        getEvolution(pokemon.evolutionId.toString())
    }

    @SuppressLint("SetTextI18n")
    private fun getEvolution(id:String){
        if(id != "null"){
            if(id != "0"){
                Glide.with(binding.ivEvolution.context).load(prefs.getImgEvolution()).into(binding.ivEvolution)
                binding.nameEvolution.text = prefs.getNameEvolution()
                binding.lvlEvolution.text = "Lvl:  ${prefs.getLvlEvolution()}"
            }else{
                binding.txtEvolution.text = "WITHOUT EVOLUTIONS"
            }
        } else{
            binding.txtEvolution.text = "WITHOUT EVOLUTIONS"
        }
    }
}