package com.example.pet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import com.example.pet.ModelClasses.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso

class UserProfile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)


        val bundle: Bundle? = intent.extras
        val uid = bundle!!.getString("uid")
        val refUsers = FirebaseDatabase.getInstance().reference.child("user").child(uid!!)

        val myusername = findViewById<TextView>(R.id.user_username)
        val email = findViewById<TextView>(R.id.user_email)
        val address = findViewById<TextView>(R.id.user_address)
        val phone = findViewById<TextView>(R.id.user_phone)
        val contactBtn = findViewById<Button>(R.id.user_contact)
        val prof = findViewById<ImageView>(R.id.user_profile)


        val x=this
        refUsers?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val user: User? = snapshot.getValue(User::class.java)
                    myusername!!.text = user!!.getUsername()
                    email!!.text = user!!.getEmail()
                    if(user!!.getAddress()==""){
                        address!!.visibility=View.GONE
                        findViewById<TextView>(R.id.user_labelA).visibility=View.GONE
                    }else{
                        address!!.text = user!!.getAddress()
                    }

                    if(user!!.getPhone()==""){
                        phone!!.visibility=View.GONE
                        findViewById<TextView>(R.id.user_labelP).visibility=View.GONE
                    }else{
                        phone!!.text = user!!.getPhone()
                    }
                    Picasso.get().load(user?.getProfile()).into(prof)

                    contactBtn.setOnClickListener{
                        val intent= Intent(x, ChatActivity::class.java)
                        intent.putExtra("friendName",user?.getUsername().toString())
                        intent.putExtra("friendId",user?.getUid())
                        intent.putExtra("friendProfile",user?.getProfile())
                        intent.putExtra("isGroup",false)
                        startActivity(intent)
                        finish()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}