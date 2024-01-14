package com.example.pet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

val navbar=findViewById<BottomNavigationView>(R.id.btmnav)
        navbar.background=null
        navbar.menu.getItem(2).isEnabled=false

    }
}