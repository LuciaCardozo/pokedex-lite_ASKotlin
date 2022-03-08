package com.example.pokedex_lite.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.pokedex_lite.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentRegisterOne.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentRegisterOne : Fragment() {

    private lateinit var vista:View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vista = inflater.inflate(R.layout.fragment_register_one, container, false)
        vista?.findViewById<Button>(R.id.buttonStepUno)?.setOnClickListener {
            stepOne()
        }
        // Inflate the layout for this fragment
        return vista
    }

    private fun stepOne(){
        val name = vista.findViewById<EditText>(R.id.firstName).text.toString()
        val lastname = vista.findViewById<EditText>(R.id.lastName).text.toString()
        if(name.isNullOrEmpty() || lastname.isNullOrEmpty()) {
            Toast.makeText(activity, "Please, complete all fields", Toast.LENGTH_SHORT).show()
        } else {
            val bundle = Bundle()
            bundle.putString("name",name)
            bundle.putString("lastname",lastname)
            parentFragmentManager.setFragmentResult("key",bundle)
            val fragmentManager = activity?.supportFragmentManager
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.fragmentContainer,FragmentRegisterTwo())
            fragmentTransaction?.commit()
        }
    }
}