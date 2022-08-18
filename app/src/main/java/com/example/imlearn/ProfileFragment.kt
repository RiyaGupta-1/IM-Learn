package com.example.imlearn

import android.Manifest
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.app.FragmentTransaction
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentResultListener
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.concurrent.fixedRateTimer


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class ProfileFragment : Fragment(){

    private var param1: String? = null
    private var param2: String? = null
    lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    private var isReadImagePermissionGranted = false
    private var isWriteImagePermissionGranted = false
    lateinit var profileImage:ImageView




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

        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
                isReadImagePermissionGranted =
                    permissions[android.Manifest.permission.READ_EXTERNAL_STORAGE]
                        ?: isReadImagePermissionGranted
                isWriteImagePermissionGranted =
                    permissions[android.Manifest.permission.WRITE_EXTERNAL_STORAGE]
                        ?: isWriteImagePermissionGranted


            }
        requestPermission()
    }

    override fun onCreateView(

    inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
      var v:View = inflater.inflate(R.layout.fragment_profile, container, false)

        var fabBtn= v.findViewById<FloatingActionButton>(R.id.fab_edit)
        var profileData= v.findViewById<TextView>(R.id.profile_data)
        var emailData= v.findViewById<TextView>(R.id.email_data)
         profileImage = v.findViewById<ImageView>(R.id.imageView)


        var args= this.arguments

        Log.d("Args", "$args")
        var inputData = args?.getString("d1")
        profileData.text = inputData.toString()

//        profileData.text= args?.getString("d1").toString()
//
//        emailData.text= args?.getString("d2").toString()

        var inputData2= args?.getString("d2")
        emailData.text= inputData2.toString()

        profileImage.setOnClickListener{
//            val intent= Intent()
//            intent.setType("image/*")
//            intent.setAction(Intent.ACTION_PICK)
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, GALLERY_REQUEST_CODE)


//                activity?.startActivityForResult(intent,
//                GALLERY_REQUEST_CODE)

//            val imageData: Uri? = intent.data
//            Glide.with(this)
//                .load(imageData)
//                .centerCrop()
//                .into(profileImage)

            //Intent.createChooser(intent,"Pick an Image")
        }


        fabBtn.setOnClickListener{
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.frameLayout, EditFragment())
            transaction.commit()

        }

        return v
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode== GALLERY_REQUEST_CODE && resultCode== RESULT_OK && data!= null){
            val imageData: Uri? = data.data
            var profileImage = view?.findViewById<ImageView>(R.id.imageView)
            profileImage?.setImageURI(imageData)

//            Glide.with(this)
//                .load(imageData)
//                .centerCrop()
//                .into(profileImage)


        }

    }


    private fun requestPermission(){

        isReadImagePermissionGranted = context?.let {
            ContextCompat.checkSelfPermission(
                it,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        } == PackageManager.PERMISSION_GRANTED

        isWriteImagePermissionGranted = context?.let {
            ContextCompat.checkSelfPermission(
                it,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        } == PackageManager.PERMISSION_GRANTED

        val permissionRequest: MutableList<String> = ArrayList()

        if(!isReadImagePermissionGranted){
            permissionRequest.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        if(!isWriteImagePermissionGranted){
            permissionRequest.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

        if(permissionRequest.isNotEmpty()){
            permissionLauncher.launch(permissionRequest.toTypedArray())
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    companion object {

        private const val GALLERY_REQUEST_CODE= 123

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


}


