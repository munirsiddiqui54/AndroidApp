package com.example.pet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.pet.ModelClasses.User
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue

class MainActivity : AppCompatActivity() {

//    var refUsers:DatabaseReference?=null
//    var firebaseUser:FirebaseUser?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navbar=findViewById<BottomNavigationView>(R.id.btmnav)
        navbar.background=null
        navbar.menu.getItem(2).isEnabled=false

//        firebaseUser=FirebaseAuth.getInstance().currentUser
//        refUsers=FirebaseDatabase.getInstance().reference.child("user").child(firebaseUser!!.uid)


        val home=HomeFragment()
        val msg=MessagesFragment()
        val notification=NotificationFragment()
        val profile=ProfileFragment()
        makeCurrentFragment(home)

        navbar.setOnItemSelectedListener{
            when(it.itemId){
                R.id.home->makeCurrentFragment(home)
                R.id.notify->makeCurrentFragment(notification)
                R.id.profile->makeCurrentFragment(profile)
                R.id.messages->makeCurrentFragment(msg)
            }
            true
        }
//        Log.d("MainActivity", refUsers!!.toString())



    }

    private fun makeCurrentFragment(fragment:Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.wrapper,fragment)
            commit()
        }
    }


}