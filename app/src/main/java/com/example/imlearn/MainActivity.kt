package com.example.imlearn

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout

//    lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
//    private var isReadImagePermissionGranted = false
//    private var isWriteImagePermissionGranted = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){
//                permissions ->
//            isReadImagePermissionGranted = permissions[android.Manifest.permission.READ_EXTERNAL_STORAGE]?: isReadImagePermissionGranted
//            isWriteImagePermissionGranted = permissions[android.Manifest.permission.WRITE_EXTERNAL_STORAGE]?: isWriteImagePermissionGranted
//   }

//        requestPermission()

        drawerLayout = findViewById(R.id.drawerLayout)
        val navView : NavigationView = findViewById(R.id.nav_view)

        toggle= ActionBarDrawerToggle(this, drawerLayout,R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()


        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {

            it.isChecked= true

            when(it.itemId){

                R.id.nav_home -> replaceFragment(HomeFragment(), it.title.toString())
                R.id.nav_profile -> replaceFragment(ProfileFragment(), it.title.toString())
                R.id.nav_contacts -> replaceFragment(ContactFragment(), it.title.toString())
                R.id.nav_login-> replaceFragment(LoginFragment(), it.title.toString())
                R.id.nav_edit -> replaceFragment(EditFragment(), it.title.toString())

            }

            true
        }

    }
    private fun replaceFragment(fragment: Fragment, title: String){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout,fragment)
        fragmentTransaction.commit()
        drawerLayout.closeDrawers()
        setTitle(title)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(toggle.onOptionsItemSelected(item)){
            return true
        }

        return super.onOptionsItemSelected(item)
    }


    fun createNewActivity(view: View) {

        val intent= Intent(this,NewActivity::class.java)
        startActivity(intent)

    }


}