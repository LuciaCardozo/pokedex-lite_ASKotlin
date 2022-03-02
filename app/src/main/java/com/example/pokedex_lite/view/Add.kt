package com.example.pokedex_lite.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.pokedex_lite.databinding.ActivityAddBinding
import com.example.pokedex_lite.model.ImageController
import com.example.pokedex_lite.model.StorageApplication.Companion.prefs
import com.google.gson.JsonParser
import io.swagger.client.apis.PokemonApi
import io.swagger.client.infrastructure.ClientException
import io.swagger.client.infrastructure.ResponseType
import io.swagger.client.infrastructure.ServerException
import io.swagger.client.models.Pokemon
import io.swagger.client.models.PokemonAbilities
import io.swagger.client.models.PokemonBody
import kotlinx.coroutines.*
import kotlinx.coroutines.android.awaitFrame

class Add : AppCompatActivity() {
    private lateinit var binding:ActivityAddBinding
    private val SELECT_ACTIVITY = 50
    private var imageUri:Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar!!.title = "NEW POKEMON"
        actionBar.subtitle ="Pokedex-Lite"
        actionBar.setDisplayHomeAsUpEnabled(true)
        binding.imageView.setOnClickListener{
            ImageController.selectPhotoFromGallery(this,SELECT_ACTIVITY)
            Toast.makeText(this, "image", Toast.LENGTH_SHORT).show()
        }
        binding.btnSave.setOnClickListener{
            addPokemon()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //DEPRECADO, CAMBIAR POR ALTENATIVA
        super.onActivityResult(requestCode, resultCode, data)

        when{
            requestCode == SELECT_ACTIVITY && resultCode == Activity.RESULT_OK -> {
                imageUri = data!!.data
                binding.imageView.setImageURI(imageUri)
            }
        }
    }

    private fun addPokemon(){
        var id:Int = prefs.getUltimoId()
        var name:String = binding.etName.text.toString()
        var lvl:String = binding.etLevel.text.toString()
        var evolutionId:String = binding.etEvolutionId.text.toString()
        var nameAbility:String = binding.etAbilityName.text.toString()
        var description:String = binding.etAbilityDescription.text.toString()
        var type:String = binding.etType.text.toString()
        var img:String = "https://www.certant.com/demo/blog/thumbs/r3.jpg"
        var userId:String =  prefs.getUserId()

        if(name=="" || lvl=="" || evolutionId=="" || nameAbility=="" || description=="" || type==""){
            Toast.makeText(this, "PLEASE COMPLETE ALL FIELDS", Toast.LENGTH_SHORT).show()
        }else{
            var pokemonAbilities = PokemonAbilities( name = nameAbility, description= description)
            var poke = Pokemon(
                id = id,
                name = name,
                lvl = lvl.toInt(),
                evolutionId = evolutionId.toInt(),
                abilities = arrayOf(pokemonAbilities),
                type = arrayOf(type),
                image = img
            )
            var newPoke = PokemonBody(pokemon = poke,userId)
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