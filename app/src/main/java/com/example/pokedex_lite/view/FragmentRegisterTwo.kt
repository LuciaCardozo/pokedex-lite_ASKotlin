package com.example.pokedex_lite.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.FragmentResultListener
import com.example.pokedex_lite.R
import io.swagger.client.apis.UserApi
import io.swagger.client.infrastructure.ClientException
import io.swagger.client.infrastructure.ServerException
import io.swagger.client.models.User
import io.swagger.client.models.UserPostRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentRegisterTwo.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentRegisterTwo : Fragment() {

    private lateinit var vista:View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vista = inflater.inflate(R.layout.fragment_register_two, container, false)
        vista.findViewById<Button>(R.id.btnBackStep).setOnClickListener {
            backStepOne()
        }
        vista.findViewById<Button>(R.id.btnAdd).setOnClickListener {
            stepTwo()
        }
        // Inflate the layout for this fragment
        return vista
    }

    private fun stepTwo() {
        val username = vista.findViewById<EditText>(R.id.username).text.toString()
        val password = vista.findViewById<EditText>(R.id.password).text.toString()
        val role = vista.findViewById<EditText>(R.id.role).text.toString()
        if (username.isNullOrEmpty() || password.isNullOrEmpty() || role.isNullOrEmpty()) {
            Toast.makeText(activity, "Please, complete all fields", Toast.LENGTH_SHORT).show()
        } else {
            parentFragmentManager.setFragmentResultListener("key",this, FragmentResultListener(){ _, bundle->
                var name = bundle.getString("name")
                var lastname = bundle.getString("lastname")
                var auxRole:Array<String> = arrayOf(role)
                var newUser = UserPostRequest(auxRole,name,lastname,username,password)
                addUser(newUser)
            })
        }
    }
    
    private fun addUser(user:UserPostRequest) {
        val apiInstance = UserApi()
        CoroutineScope(Dispatchers.IO).async {
            try {
                val result : User = apiInstance.userPost(user)
                println(result)
                Toast.makeText(activity, "Added user ", Toast.LENGTH_SHORT).show()
            } catch (e: ClientException) {
                println("4xx response calling UserApi#userPost")
                e.printStackTrace()
            } catch (e: ServerException) {
                println("5xx response calling UserApi#userPost")
                e.printStackTrace()
            } finally {
                var intent = Intent( activity?.baseContext?.applicationContext, Login::class.java)
                startActivity(intent)
            }
        }
    }

    private fun backStepOne() {
        val fragmentManager = activity?.supportFragmentManager
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.fragmentContainer,FragmentRegisterOne())
        fragmentTransaction?.commit()
    }
}