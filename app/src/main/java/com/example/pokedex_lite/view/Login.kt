package com.example.pokedex_lite.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.pokedex_lite.model.StorageApplication.Companion.prefs

import com.example.pokedex_lite.databinding.ActivityMainBinding
import com.example.pokedex_lite.model.Loading
import io.swagger.client.apis.SecurityApi
import io.swagger.client.infrastructure.ClientException
import io.swagger.client.infrastructure.ServerException
import io.swagger.client.models.LoginBody
import io.swagger.client.models.LoginPostResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Login : AppCompatActivity() {
    private val apiInstanceLogin = SecurityApi()
    private lateinit var binding:ActivityMainBinding
    private var loadingSpinner = Loading(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val actionBar = supportActionBar
        actionBar!!.title = "Pokedex-Lite"
        prefs.wipe()
        binding.btnUserUno.setOnClickListener{
            autocomplete("trainer","password")
        }
        binding.buttonLogin.setOnClickListener{
            val username:String = binding.textUserName.text.toString()
            val password:String = binding.textPassword.text.toString()
            login(username,password)
            binding.textUserName.setText("")
            binding.textPassword.setText("")
        }
    }

    private fun autocomplete(name:String,password:String){
        binding.textUserName.setText(name)
        binding.textPassword.setText(password)
    }

    private fun login(username:String,password: String){
        loadingSpinner.startLoading()
        if (username == "" || password == ""){
            Toast.makeText(this, "Por favor complete los campos", Toast.LENGTH_SHORT).show()
        }else{
            CoroutineScope(Dispatchers.IO).launch {
            val user = LoginBody(username = username,password = password)
                try {
                    val result: LoginPostResponse = apiInstanceLogin.loginPOST(user)
                    println(result)
                    if (result != null) {
                        prefs.saveUserId(result.userId as String)
                        prefs.saveUsername(result.username as String)
                        intent = Intent(this@Login, Home::class.java)
                        startActivity(intent)
                    }
                } catch (e: ClientException) {
                    println("4xx response calling SecurityApi#loginPOST")
                    e.printStackTrace()
                } catch (e: ServerException) {
                    println("5xx response calling SecurityApi#loginPOST")
                    e.printStackTrace()
                }finally {
                    loadingSpinner.isDismiss()
                }
            }
        }
    }


}