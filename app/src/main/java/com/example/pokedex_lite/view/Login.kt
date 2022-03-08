package com.example.pokedex_lite.view

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
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
            autocomplete()
        }
        binding.buttonLogin.setOnClickListener{
            val username:String = binding.textUserName.text.toString()
            val password:String = binding.textPassword.text.toString()
            login(username,password)
            binding.textUserName.setText("")
            binding.textPassword.setText("")
        }
        binding.buttonRegister.setOnClickListener {
            intent = Intent(this@Login, Register::class.java)
            startActivity(intent)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun autocomplete(){
        binding.textUserName.setText("trainer")
        binding.textPassword.setText("password")
    }

    private fun login(username:String,password: String){

        if (username == "" || password == ""){
            Toast.makeText(this, "Por favor complete los campos", Toast.LENGTH_SHORT).show()
        }else{
            loadingSpinner.startLoading()
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

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (event.action == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                val alertDialog:AlertDialog.Builder = AlertDialog.Builder(this)
                alertDialog.setMessage("Do you want to exit the application?")
                    .setPositiveButton("yes", DialogInterface.OnClickListener { _, _ ->
                        intent = Intent(Intent.ACTION_MAIN)
                        intent.addCategory(Intent.CATEGORY_HOME)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                    }).setNegativeButton("no",DialogInterface.OnClickListener { dialogInterface, _ ->
                        dialogInterface.dismiss()
                    }).show()
            }
        }
        return true
    }

}