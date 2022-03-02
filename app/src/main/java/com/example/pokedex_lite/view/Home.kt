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
import kotlinx.coroutines.*


class Home : AppCompatActivity() {
    private var listPokemon:MutableList<Pokemon> = mutableListOf()
    private lateinit var binding: ActivityHomeBinding
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = prefs.getUsername()
        binding.textUsername.text = "Welcome $username"
        val userId = prefs.getUserId()
        getPokemon(userId)
        binding.button.setOnClickListener{
            intent = Intent(this@Home, Add::class.java)
            startActivity(intent)
        }
    }

    private fun getPokemon(userId:String){
       CoroutineScope(Dispatchers.IO).launch {
            val apiInstancePokemon = PokemonApi()
            try {
                val result =  apiInstancePokemon.pokemonGet(userId)

                    for (pokemon in result){
                        if(pokemon != null){
                            listPokemon.add(pokemon)
                        }
                    }
                //Se ejecuta en el hilo principal :)
                runOnUiThread{
                    initRecyclerView(listPokemon)
                    proximoId(listPokemon)
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
        if(pokemonEvolution.name != ""){
            val imageEvolution = ImagePokemonProvider.addImageEvolution(pokemonEvolution.image.toString())
            prefs.saveImgEvolution(imageEvolution)
            prefs.saveNameEvolution(pokemonEvolution.name.toString())
            prefs.saveLvlEvolution(pokemonEvolution.lvl.toString())
        }
        intent = Intent(this@Home, Detail::class.java)
        intent.putExtra("pokemon",pokemon)
        startActivity(intent)
    }

    fun proximoId(lista:List<Pokemon>){
        var ultimoId = lista.size
        ultimoId++
        prefs.saveUltimoId(ultimoId++)
    }
}


