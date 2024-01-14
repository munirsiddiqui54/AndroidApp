package com.example.pet

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Pair
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val login=findViewById<Button>(R.id.login)

        val kitten=findViewById<View>(R.id.kitten)
        val head=findViewById<TextView>(R.id.head)
        val box=findViewById<LinearLayout>(R.id.layout)
        val registerbtn=findViewById<Button>(R.id.registerbtn)

        val topanimate= AnimationUtils.loadAnimation(this,R.anim.top_animate)
        box.animation=topanimate
        head.animation=topanimate

        login.setOnClickListener{
            val pairs: Array<Pair<View, String>?> = arrayOfNulls(1)

            pairs[0]= Pair<View,String>(kitten,"kitten")


            var op: ActivityOptions?=null
            op= ActivityOptions.makeSceneTransitionAnimation(this,*pairs)

            startActivity(Intent(this, com.example.pet.login::class.java),op.toBundle())
        }

        registerbtn.setOnClickListener{
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }


}