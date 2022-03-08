package com.example.pokedex_lite.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.vectordrawable.animated.R
import com.example.pokedex_lite.databinding.ActivityRegisterBinding

class Register : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar!!.title = "REGISTER"
        actionBar.subtitle ="Pokedex-Lite"
        actionBar.setDisplayHomeAsUpEnabled(true)
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(com.example.pokedex_lite.R.id.fragmentContainer,FragmentRegisterOne())
        fragmentTransaction.commit()
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}