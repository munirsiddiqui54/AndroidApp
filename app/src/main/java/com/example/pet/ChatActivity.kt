package com.example.pet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import org.w3c.dom.Text

class ChatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)


        val bundle: Bundle?=intent.extras
        val name=bundle!!.getString("friendName")

        val friendProfile=bundle!!.getString("friendProfile")
        val img:ImageView=findViewById(R.id.friendProfile)

        Picasso.get().load(friendProfile).placeholder(R.drawable.myprofile).into(img)

        val text=findViewById<TextView>(R.id.friendName)
        text.text=name
    }
}