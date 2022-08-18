package com.example.imlearn

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        var baseActivity:MainActivity ?= activity as MainActivity
        var context: Context? =getContext()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        var loginBtn = view.findViewById<Button>(R.id.login)
        var enterName = view.findViewById<EditText>(R.id.entername)
        var enterMail = view.findViewById<EditText>(R.id.entermail)
        var saveBtn = view.findViewById<Button>(R.id.saveBtn)
        val name = enterName.getText().toString()
        val email = enterMail.getText().toString()



        loginBtn.setOnClickListener {
            val db = context?.let { it1 -> DatabaseHelper(it1, null) }
            db?.addData(name, email)

            Toast.makeText(context, name + " added to database", Toast.LENGTH_LONG).show()

            enterName.text.clear()
            enterMail.text.clear()

        }

        saveBtn.setOnClickListener {

            val db = context?.let { it1 -> DatabaseHelper(it1, null) }

            val name2 = enterName.getText().toString()
            Log.d("name2", "$name2")
            val email2 = enterMail.getText().toString()

//            if (name.isNotEmpty() && email.isNotEmpty()) {
//                if (db!!.Login(enterName.toString(), enterMail.toString())) {
//                    Toast.makeText(context, "Successfully Logged In", Toast.LENGTH_LONG)
//                        .show();
//                } else {
//                    Toast.makeText(context, "Invalid Username/Password", Toast.LENGTH_LONG)
//                        .show();
//                }
//            }
                var args = Bundle()
                args.putString("d1", name2)
                //Log.e(TAG, "onCreateView: failure")
                args.putString("d2", email2)
//            parentFragmentManager.setFragmentResult("dataFromLogin",result)

                val fragment = ProfileFragment()
                fragment.arguments = args
                Log.d("Argument", "$args")
                fragmentManager?.beginTransaction()?.replace(R.id.frameLayout, fragment)?.commit()

            }


            // super.onViewCreated(view, savedInstanceState)
        }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters

    }
}