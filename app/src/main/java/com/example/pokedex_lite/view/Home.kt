package com.example.pokedex_lite.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex_lite.model.StorageApplication.Companion.prefs
import com.example.pokedex_lite.R
import com.example.pokedex_lite.adapter.PokemonAdapter
import com.example.pokedex_lite.databinding.ActivityHomeBinding
import com.example.pokedex_lite.model.ImagePokemonProvider
import io.swagger.client.apis.PokemonApi
import io.swagger.client.infrastructure.ClientException
import io.swagger.client.infrastructure.ServerException
import io.swagger.client.models.Pokemon
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class Home : AppCompatActivity() {
    private var listPokemon:MutableList<Pokemon> = mutableListOf()
    private lateinit var binding: ActivityHomeBinding
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra("username")
        binding.textUsername.text = "Welcome $username"
        val userId = intent.getStringExtra("userId")
        getPokemon(userId.toString(),listPokemon)
        Thread.sleep(1000)
        println(listPokemon)
        initRecyclerView(listPokemon)

    }

    private fun getPokemon(userId:String, list: MutableList<Pokemon>){
        CoroutineScope(Dispatchers.IO).launch {
            val apiInstancePokemon = PokemonApi()
            try {
                val result: Array<Pokemon> = apiInstancePokemon.pokemonGet(userId)
                for (poke in result) {
                    //this validation is not necessary but in my case it's
                        // because I have a null in the list =D
                    if (poke != null) {
                        list.add(poke)
                    }
                }
            } catch (e: ClientException) {
                println("4xx response calling PokemonApi#pokemonGet")
                e.printStackTrace()

            } catch (e: ServerException) {
                println("5xx response calling PokemonApi#pokemonGet")
            }
        }
    }

    private fun initRecyclerView(list:List<Pokemon>){
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerPokemon)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = PokemonAdapter(list) { pokemon -> detailPokemon(pokemon) }
    }

    private fun getPokemonIdEvolution(idEvolution:Int?,list: MutableList<Pokemon>):Pokemon{
        for (pokemon in list){
            if(pokemon.id == idEvolution){
                return pokemon
            }
        }
        return Pokemon()
    }

    private fun detailPokemon(pokemon:Pokemon){
        //POKEMON EVOLUTION
        val pokemonEvolution:Pokemon = getPokemonIdEvolution(pokemon.evolutionId,listPokemon)
        if(pokemonEvolution.name != null){
            val imageEvolution = ImagePokemonProvider.addImageEvolution(pokemonEvolution.image.toString())
            prefs.saveImgEvolution(imageEvolution)
            prefs.saveNameEvolution(pokemonEvolution.name.toString())
            prefs.saveLvlEvolution(pokemonEvolution.lvl.toString())
        }
        intent = Intent(this@Home, Detail::class.java)
        intent.putExtra("pokemon",pokemon)
        startActivity(intent)
    }
}


