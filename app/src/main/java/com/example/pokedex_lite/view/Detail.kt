package com.example.pokedex_lite.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokedex_lite.R
import com.example.pokedex_lite.adapter.PokemonAbilityAdapter
import com.example.pokedex_lite.databinding.ActivityDetailBinding
import com.example.pokedex_lite.model.ImagePokemonProvider
import com.example.pokedex_lite.model.StorageApplication.Companion.prefs
import io.swagger.client.models.Pokemon
import io.swagger.client.models.PokemonAbilities

class Detail : AppCompatActivity() {
    private lateinit var binding:ActivityDetailBinding
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar!!.title = "DETAIL"
        actionBar.subtitle ="Pokedex-Lite"
        actionBar.setDisplayHomeAsUpEnabled(true)
        val pokemon:Pokemon = intent.getSerializableExtra("pokemon") as Pokemon
        uploadData(pokemon)
        binding.btnEdit.setOnClickListener{
            intent = Intent(this, Edit::class.java)
            intent.putExtra("pokemon",pokemon)
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        intent = Intent(this, Home::class.java)
        startActivity(intent)
        //onBackPressed()
        return true
    }

    private fun initRecyclerView(list:List<PokemonAbilities>){
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewAbilities)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = PokemonAbilityAdapter(list)
    }

    @SuppressLint("SetTextI18n")
    private fun uploadData(pokemon:Pokemon){
        val types:MutableList<String> = mutableListOf()
        for(type in pokemon.type!!){
            types.add(type)
        }
        val pokemonAbilities:MutableList<PokemonAbilities> = mutableListOf()
        for (ability in pokemon.abilities!!){
            pokemonAbilities.add(PokemonAbilities(name=ability.name,description=ability.description))
        }
        initRecyclerView(pokemonAbilities)
        binding.tvPokeType.text = "Types: ${types.toString().replace("[","").replace("]","")}"
        binding.tvPokeName.text = pokemon.name
        binding.tvPokeLvl.text = "Lvl: ${pokemon.lvl}"
        Glide.with(binding.ivPoke.context).load(ImagePokemonProvider.addImage(pokemon.image.toString())).into(binding.ivPoke)
        getEvolution(pokemon.evolutionId.toString())
    }

    @SuppressLint("SetTextI18n")
    private fun getEvolution(id:String){
        if(id != "null"){
            if(id != "0"){
                val imgEvolution = prefs.getImgEvolution()
                val nameEvolution = prefs.getNameEvolution()
                val lvlEvolution = "Lvl: ${prefs.getLvlEvolution()}"
                if(nameEvolution == "null"){
                    binding.txtEvolution.text = "NO EVOLUTION DETAILS"
                }else{
                    Glide.with(binding.ivEvolution.context).load(imgEvolution).into(binding.ivEvolution)
                    binding.nameEvolution.text = nameEvolution
                    binding.lvlEvolution.text = lvlEvolution
                }
            }else{
                binding.txtEvolution.text = "WITHOUT EVOLUTIONS"
            }
        } else{
            binding.txtEvolution.text = "WITHOUT EVOLUTIONS"
        }
    }
}