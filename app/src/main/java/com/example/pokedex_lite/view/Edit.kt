package com.example.pokedex_lite.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.pokedex_lite.databinding.ActivityEditBinding
import io.swagger.client.apis.PokemonApi
import io.swagger.client.infrastructure.ClientException
import io.swagger.client.infrastructure.ServerException
import io.swagger.client.models.Pokemon
import io.swagger.client.models.PokemonAbilities
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import okhttp3.internal.concat

class Edit : AppCompatActivity() {
    private lateinit var binding: ActivityEditBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val actionBar = supportActionBar
        actionBar!!.title = "EDIT"
        actionBar.subtitle ="Pokedex-Lite"
        actionBar.setDisplayHomeAsUpEnabled(true)
        val pokemon: Pokemon = intent.getSerializableExtra("pokemon") as Pokemon
        seeEdit(pokemon)
        binding.btedit.setOnClickListener{
            uploadData(pokemon)
        }
        binding.btnRemoveType.setOnClickListener {
            removeType(pokemon)
        }
        binding.btnAddType.setOnClickListener {
            addType(pokemon)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun seeEdit(pokemon:Pokemon){
        binding.editName.hint = pokemon.name
        binding.edIdEvolution.hint = pokemon.evolutionId.toString()
        uploadListType(pokemon)
    }

    private fun uploadListType(pokemon:Pokemon) {
        val listViewType = binding.lvTypes
        val arrayAdapter:ArrayAdapter<*>
        val types:MutableList<String> = mutableListOf()
        for (type in pokemon.type!!) {
            types.add(type)
        }
        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, types)
        listViewType.adapter = arrayAdapter
        listViewType.setOnItemClickListener { parent, _, position, _ ->
            binding.edType.setText(parent.getItemAtPosition(position).toString())
        }
    }

    private fun uploadData(pokemon:Pokemon) {
        val name = binding.editName.text.toString()
        val nameAbility = binding.edNameAbility.text.toString()
        val description   = binding.edDescription.text.toString()
        val idEvolution = binding.edIdEvolution.text.toString()
        if(name != "" || nameAbility != "" || description != "" || idEvolution != ""){
            pokemon.name = if(name=="") pokemon.name else name
            pokemon.type = pokemon.type
            pokemon.evolutionId = if (idEvolution=="") pokemon.evolutionId else idEvolution.toInt()
            pokemon.abilities = addAbility(pokemon,nameAbility,description)
            pokemon.id = pokemon.id
            pokemon.lvl = pokemon.lvl
            pokemon.image = pokemon.image
        }else{
            Toast.makeText(this, "No change was made", Toast.LENGTH_SHORT).show()
        }
        println(pokemon)
        editPokemon(pokemon)
    }

    private fun addAbility(pokemon:Pokemon, name:String, description:String):Array<PokemonAbilities> {
        val pokeAux:PokemonAbilities

        if (name != "" && description != "") {
            pokeAux = PokemonAbilities(name=name, description=description)
            pokemon.abilities = pokemon.abilities!!+pokeAux
        } else if (name == "" && description != "" || name != "" && description == ""){
            Toast.makeText(this, "Please, complete all fields of abilities", Toast.LENGTH_SHORT).show()
        }
        return pokemon.abilities!!
    }

    private fun editPokemon(pokemon:Pokemon){
        val apiInstance = PokemonApi()
        CoroutineScope(Dispatchers.IO).async {
            try {
                apiInstance.pokemonPut(pokemon)
            } catch (e: ClientException) {
                println("4xx response calling PokemonApi#pokemonPut")
                e.printStackTrace()
            } catch (e: ServerException) {
                println("5xx response calling PokemonApi#pokemonPut")
                e.printStackTrace()
            } finally {
                intent = Intent(this@Edit, Detail::class.java)
                intent.putExtra("pokemon",pokemon)
                startActivity(intent)
            }
        }
    }

    private fun addType(pokemon:Pokemon){
        val type = binding.edType.text.toString()
        if(type!="") {
            val exist = pokemon.type!!.indexOf(type)
            if(exist < 0) {
                pokemon.type = pokemon.type!!.concat(type)
                uploadListType(pokemon)
                binding.edType.setText("")
            } else {
                Toast.makeText(this, "It already exists $type", Toast.LENGTH_SHORT).show()
                binding.edType.setText("")
            }
        }
    }

    private fun removeType(pokemon:Pokemon) {
        val auxList:MutableList<String> = mutableListOf()
        val type = binding.edType.text.toString()
        if(type != "") {
            val exist = pokemon.type!!.indexOf(type)
            if(exist>-1) {
                for (pokeType in pokemon.type!!) {
                    if(pokeType != type) {
                        auxList.add(pokeType)
                    }
                }
                val poke: Array<String> = auxList.toTypedArray()
                pokemon.type = poke
                binding.edType.setText("")
            } else {
                binding.edType.setText("")
            }
        }
        uploadListType(pokemon)
    }

}