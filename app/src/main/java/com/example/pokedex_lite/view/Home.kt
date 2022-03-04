package com.example.pokedex_lite.view

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
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

class Home : AppCompatActivity(), SearchView.OnQueryTextListener {
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
        binding.button.setOnClickListener {
            intent = Intent(this@Home, Add::class.java)
            startActivity(intent)
        }
        binding.searchView.setOnQueryTextListener(this)
        binding.btnLogout.setOnClickListener {
            logout()
        }
    }

    private fun logout(){
        val alertDialog:AlertDialog.Builder = AlertDialog.Builder(this@Home)
        alertDialog.setMessage("Do you want to log out?")
            .setPositiveButton("yes", DialogInterface.OnClickListener { _, _ ->
                intent = Intent(this,Login::class.java)
                startActivity(intent)
            }).setNegativeButton("no",DialogInterface.OnClickListener { dialogInterface, _ ->
                dialogInterface.dismiss()
            }).show()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if(event.action == KeyEvent.ACTION_DOWN) {
            if(keyCode == KeyEvent.KEYCODE_BACK) {
                logout()
            }
        }
        return true
    }

    private fun getPokemon(userId:String){
       CoroutineScope(Dispatchers.IO).launch {
            val apiInstancePokemon = PokemonApi()
            try {
                val result =  apiInstancePokemon.pokemonGet(userId)
                    for (pokemon in result) {
                        if (pokemon != null) {
                            listPokemon.add(pokemon)
                        }
                    }
                //Se ejecuta en el hilo principal :)
                runOnUiThread {
                    initRecyclerView(listPokemon)
                    nextId(listPokemon)
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

    private fun getPokemonIdEvolution(idEvolution:Int?,list: MutableList<Pokemon>):Pokemon {
        for (pokemon in list){
            if (pokemon.id == idEvolution) {
                return pokemon
            }
        }
        return Pokemon()
    }

    private fun detailPokemon(pokemon:Pokemon) {
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

    private fun nextId(list:List<Pokemon>) {
        var ultimoId = list.size
        ultimoId++
        prefs.saveUltimoId(ultimoId)
    }

    private fun searchByName(query:String) {
        val newList = listPokemon.filter { it.name?.contains(query,ignoreCase = true) ?: null!! }
        initRecyclerView(newList)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(!query.isNullOrEmpty()) {
            searchByName(query.lowercase())
        }
        closeVirtualKeyboard()
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if(newText.isNullOrEmpty()) {
            initRecyclerView(listPokemon)
            //closeVirtualKeyboard()
        }
        return true
    }

    private fun closeVirtualKeyboard(){
        val view = this.currentFocus
        if(view != null) {
            val im = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            im.hideSoftInputFromWindow(view.windowToken, 0)
        } else {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        }
    }
}


