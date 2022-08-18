package com.example.imlearn

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private lateinit var adapter: MyAdapter
private lateinit var recyclerView: RecyclerView

class ContactFragment : Fragment(), MyAdapter.listBtnClicked{

    lateinit var callBtn:Button
lateinit var number: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var v:View= inflater.inflate(R.layout.fragment_contact, container, false)
         //callBtn= v.findViewById<Button>(R.id.list_btn)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager= LinearLayoutManager(context)
        recyclerView= view.findViewById(R.id.recycler_view)
        getUser()

//        callBtn.setOnClickListener {
//            onCallButtonClicked(data.users)
//        }

    }

   private fun getUser(){

        val user: Call<Contacts> = UserService.userInstance.getUsers(1)

        user.enqueue(object : Callback<Contacts> {
            override fun onResponse(call: Call<Contacts>, response: Response<Contacts>) {

                val data: Contacts? = response.body()
                if(user!=null){
                    //Log.d("DATACHEEZE", data.toString())
                    if (data != null) {
                        //adapter= MyAdapter(this@ContactFragment,context, data.users)
                        adapter= MyAdapter(this@ContactFragment, this@ContactFragment, data.users)
                    }
                    recyclerView.adapter= adapter
                    recyclerView.layoutManager= LinearLayoutManager(activity)

                }

                

            }


            override fun onFailure(call: Call<Contacts>, t: Throwable) {

                Log.d("Fail", "onFailure: "+ t.message)
                Toast.makeText(context, "Error "+t.message, Toast.LENGTH_SHORT).show()


            }

        })
    }

   override fun onCallButtonClicked(user: User){
        number= user.phone.toString().trim()
       if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
           val intent= Intent(Intent.ACTION_CALL, Uri.parse("tel:"+ Uri.encode(number)))
           startActivity(intent)
       }
       else{
           ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.CALL_PHONE), REQUEST_CALL)
       }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ){

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CALL && grantResults.isNotEmpty()) {
            for (i in grantResults.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    val intent= Intent(Intent.ACTION_CALL, Uri.parse("tel:"+ Uri.encode(number.toString())))
                    startActivity(intent)
                   // onCallButtonClicked()
                } else {
                    Toast.makeText(context, "Call Permission Denied", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

    }


    companion object {

        private const val REQUEST_CALL= 100
    }
}




