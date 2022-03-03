package com.example.pokedex_lite.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.pokedex_lite.R
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
        var pokemon: Pokemon = intent.getSerializableExtra("pokemon") as Pokemon
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

    fun uploadListType(pokemon:Pokemon) {
        var listViewType = binding.lvTypes
        var arrayAdapter:ArrayAdapter<*>
        var types:MutableList<String> = mutableListOf()
        for (type in pokemon.type!!) {
            types.add(type)
        }
        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, types)
        listViewType.adapter = arrayAdapter
        listViewType.setOnItemClickListener(){ parent,view,position,id->
            binding.edType.setText(parent.getItemAtPosition(position).toString())
        }
    }

    private fun uploadData(pokemon:Pokemon) {
        var name = binding.editName.text.toString()
        var nameAbility = binding.edNameAbility.text.toString()
        var description   = binding.edDescription.text.toString()
        var idEvolution = binding.edIdEvolution.text.toString()
        if(name != "" || nameAbility != "" || description != "" || idEvolution != ""){
            pokemon.name = if(name=="") pokemon.name else name
            pokemon.type = pokemon.type
            pokemon.evolutionId = if (idEvolution=="") pokemon.evolutionId else idEvolution.toInt()
            pokemon.abilities = addAbility(pokemon,nameAbility,description)
            pokemon.id = pokemon.id
            pokemon.lvl = pokemon.lvl
            pokemon.image = pokemon.image
        }else{
            Toast.makeText(this, "No se realizo ningun cambio", Toast.LENGTH_SHORT).show()
        }
        println(pokemon)
        editPokemon(pokemon)
    }

    private fun addAbility(pokemon:Pokemon, name:String, description:String):Array<PokemonAbilities> {
        var pokeAux:PokemonAbilities

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
        var type = binding.edType.text.toString()
        if(type!="") {
            var exist = pokemon.type!!.indexOf(type)
            if(exist < 0) {
                pokemon.type = pokemon.type!!.concat(type)
                uploadListType(pokemon)
                binding.edType.setText("")
            } else {
                Toast.makeText(this, "Ya existe el tipo $type", Toast.LENGTH_SHORT).show()
                binding.edType.setText("")
            }
        }
    }

    private fun removeType(pokemon:Pokemon) {
        var auxList:MutableList<String> = mutableListOf()
        var type = binding.edType.text.toString()
        if(type != "") {
            var exist = pokemon.type!!.indexOf(type)
            if(exist>-1) {
                for (pokeType in pokemon.type!!) {
                    if(pokeType != type) {
                        auxList.add(pokeType)
                    }
                }
                var poke: Array<String> = auxList.toTypedArray()
                pokemon.type = poke
                binding.edType.setText("")
            } else {
                binding.edType.setText("")
            }
        }
        uploadListType(pokemon)
    }

}