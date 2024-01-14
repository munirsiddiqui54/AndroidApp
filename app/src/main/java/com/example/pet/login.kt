package com.example.pet

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Pair
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btn=findViewById<Button>(R.id.signup)
        val kitten=findViewById<View>(R.id.cat)
        val head=findViewById<TextView>(R.id.heading)
        val box=findViewById<LinearLayout>(R.id.box)

        val loginbtn=findViewById<Button>(R.id.loginbtn)

        val topanimate= AnimationUtils.loadAnimation(this,R.anim.bottom_animate)
        box.animation=topanimate
        head.animation=topanimate

        btn.setOnClickListener{
            val pairs: Array<Pair<View, String>?> = arrayOfNulls(1)

            pairs[0]= Pair<View,String>(kitten,"kitten")

            var op: ActivityOptions?=null
            op= ActivityOptions.makeSceneTransitionAnimation(this,*pairs)

            startActivity(Intent(this,Register::class.java),op.toBundle())
        }

        loginbtn.setOnClickListener{
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }

    }
}