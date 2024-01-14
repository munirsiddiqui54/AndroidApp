package com.example.pet

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Pair
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.TextView

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)


        val topanimate= AnimationUtils.loadAnimation(this,R.anim.top_animate)
        val bottomanimate= AnimationUtils.loadAnimation(this,R.anim.bottom_animate)

        val img=findViewById<View>(R.id.img)
        val text2=findViewById<TextView>(R.id.textView2)
        val text3=findViewById<TextView>(R.id.textView3)

        img.animation=topanimate
        text2.animation=bottomanimate
        text3.animation=bottomanimate

        val kitten=findViewById<View>(R.id.img)

        Handler(Looper.getMainLooper()).postDelayed({
            val pairs: Array<Pair<View,String>?> = arrayOfNulls(1)

            pairs[0]=Pair<View,String>(kitten,"kitten")

            var op:ActivityOptions?=null
            op= ActivityOptions.makeSceneTransitionAnimation(this,*pairs)

            startActivity(Intent(this,login::class.java),op.toBundle())

            finish()
        },2000)
    }
}