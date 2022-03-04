package com.example.pokedex_lite.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.pokedex_lite.databinding.ActivityAddBinding
import com.example.pokedex_lite.model.StorageApplication.Companion.prefs
import io.swagger.client.apis.PokemonApi
import io.swagger.client.infrastructure.ClientException
import io.swagger.client.infrastructure.ServerException
import io.swagger.client.models.Pokemon
import io.swagger.client.models.PokemonAbilities
import io.swagger.client.models.PokemonBody
import kotlinx.coroutines.*

class Add : AppCompatActivity() {
    private lateinit var binding:ActivityAddBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar!!.title = "NEW POKEMON"
        actionBar.subtitle ="Pokedex-Lite"
        actionBar.setDisplayHomeAsUpEnabled(true)
        binding.imageView.setOnClickListener{
            selectPhotoFromGallery()
        }
        binding.btnSave.setOnClickListener{
            addPokemon()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private val startActivityForGallery = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result ->
        if(result.resultCode == Activity.RESULT_OK) {
            val data = result.data?.data
            binding.imageView.setImageURI(data)
        }
    }

    private fun selectPhotoFromGallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForGallery.launch(intent)
    }

    private fun addPokemon(){
        val id:Int = prefs.getUltimoId()
        val name = binding.etName.text.toString()
        val lvl = binding.etLevel.text.toString()
        val evolutionId = binding.etEvolutionId.text.toString()
        val nameAbility = binding.etAbilityName.text.toString()
        val description = binding.etAbilityDescription.text.toString()
        val type:String = binding.etType.text.toString()
        val img = "https://www.certant.com/demo/blog/thumbs/r3.jpg"
        val userId:String =  prefs.getUserId()

        if(name=="" || lvl=="" || evolutionId=="" || nameAbility=="" || description=="" || type==""){
            Toast.makeText(this, "PLEASE COMPLETE ALL FIELDS", Toast.LENGTH_SHORT).show()
        }else{
            val pokemonAbilities = PokemonAbilities( name = nameAbility, description= description)
            val poke = Pokemon(
                id = id,
                name = name,
                lvl = lvl.toInt(),
                evolutionId = evolutionId.toInt(),
                abilities = arrayOf(pokemonAbilities),
                type = arrayOf(type),
                image = img
            )
            val newPoke = PokemonBody(pokemon = poke,userId)
            addPoke(newPoke)
        }
    }

    private fun addPoke(newPokemon:PokemonBody?) {
        val apiInstance = PokemonApi()
        CoroutineScope(Dispatchers.IO).async {
            try {
              apiInstance.pokemonPost(newPokemon)
            } catch (e: ClientException) {
                println("4xx response calling PokemonApi#pokemonPost")
                e.printStackTrace()
            } catch (e: ServerException) {
                println("5xx response calling PokemonApi#pokemonPost")
                e.printStackTrace()
            } finally {
                intent = Intent(this@Add, Home::class.java)
                startActivity(intent)
            }
        }
    }

}