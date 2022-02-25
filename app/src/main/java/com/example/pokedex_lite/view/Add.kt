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
import io.swagger.client.apis.PokemonApi
import io.swagger.client.infrastructure.ClientException
import io.swagger.client.infrastructure.ServerException
import io.swagger.client.models.PokemonBody
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
            intent = Intent(this@Add, Home::class.java)
            startActivity(intent)
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

    fun addPokemon(){
        var id:Int = prefs.getUltimoId()
        var name:String = binding.etName.text.toString()
        var lvl:String = binding.etLevel.text.toString()
        var evolutionId:String = binding.etEvolutionId.text.toString()
        var nameAbility:String = binding.etAbilityName.text.toString()
        var description:String = binding.etAbilityDescription.text.toString()
        var type = binding.etType.text.toString()
        var img:String = "img.png"
        var userId:String =  prefs.getUserId()

        var newPokemon =
            "{'Pokemon':{'id': $id," +
                "'name': '$name'," +
                "'lvl': $lvl," +
                "'evolutionId':$evolutionId," +
                "'abilities':[{'name':'$nameAbility'," +
                "'description':'$description'}]," +
                "'type':['$type']," +
                "'image':'$img'}," +
            "'userId':'$userId'}"
        
        println(newPokemon)
    }

    fun addPokemon(newPokemon:PokemonBody){
        val apiInstance = PokemonApi()
        CoroutineScope(Dispatchers.IO).launch {
            val body : PokemonBody =  newPokemon
            try {
                apiInstance.pokemonPost(body)
            } catch (e: ClientException) {
                println("4xx response calling PokemonApi#pokemonPost")
                e.printStackTrace()
            } catch (e: ServerException) {
                println("5xx response calling PokemonApi#pokemonPost")
                e.printStackTrace()
            }
        }

    }

}