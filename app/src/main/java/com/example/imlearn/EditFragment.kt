package com.example.imlearn

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast


class EditFragment : Fragment() {


    override fun onAttach(context: Context) {
        super.onAttach(context)
        var baseActivity:MainActivity ?= activity as MainActivity
        var context: Context? =getContext()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var v:View= inflater.inflate(R.layout.fragment_edit, container, false)

        var edName= v.findViewById<EditText>(R.id.edName)
        var edEmail= v.findViewById<EditText>(R.id.edEmail)
        var updateBtn= v.findViewById<Button>(R.id.updateBtn)

        updateBtn.setOnClickListener {
           // updateUser()
            var name= edName.text.toString()
            var email= edEmail.text.toString()
//
//            if(name==?.name)
            val db = context?.let { it1 -> DatabaseHelper(it1,null) }
            if (db?.updateData(name, email) == true){

                Toast.makeText(context, "Data Updated", Toast.LENGTH_LONG).show()
            }
            else{
                Log.d("Update", "No data updated")
            }


        }



        return v
    }

//    private fun updateUser() {
//
//        var name= edName.text.toString()
//
//    }

    companion object {

    }
}