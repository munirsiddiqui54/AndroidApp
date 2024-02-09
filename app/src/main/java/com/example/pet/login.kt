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
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class login : AppCompatActivity() {
    private lateinit var myauth: FirebaseAuth
    private lateinit var refusers: DatabaseReference
    private var fbId:String=""
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

        myauth=FirebaseAuth.getInstance()

        btn.setOnClickListener{
            val pairs: Array<Pair<View, String>?> = arrayOfNulls(1)

            pairs[0]= Pair<View,String>(kitten,"kitten")

            var op: ActivityOptions?=null
            op= ActivityOptions.makeSceneTransitionAnimation(this,*pairs)

            startActivity(Intent(this,Register::class.java),op.toBundle())
        }

        loginbtn.setOnClickListener{
            loginUser()
        }

    }


    private fun loginUser(){
        val email:String=findViewById<TextInputEditText>(R.id.lemail).text.toString()
        val password:String=findViewById<TextInputEditText>(R.id.lpassword).text.toString()

        if(email.equals("")){
            Toast.makeText(this,"Please Provide email", Toast.LENGTH_SHORT).show()
        }
       else if(password.equals("")){
            Toast.makeText(this,"Please Provide password", Toast.LENGTH_SHORT).show()
        }else{
            myauth.signInWithEmailAndPassword(email,password).addOnCompleteListener{
                task->
                if(task.isSuccessful){
                    val intent=Intent(this,MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    Toast.makeText(this,"Welcome", Toast.LENGTH_SHORT).show()
                    startActivity(intent)
                    finish()
                }else{
                    Toast.makeText(this,task.exception?.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }
//            myauth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{task->
//                if(task.isSuccessful){
//                    fbId=myauth.currentUser!!.uid
//                    refusers= FirebaseDatabase.getInstance().reference.child("user").child(fbId)
//                    val userHashMap=HashMap<String,Any>()
//                    userHashMap["uid"]=fbId
//                    userHashMap["username"]=username
//                    userHashMap["profile"]="https://firebasestorage.googleapis.com/v0/b/petcompass-b0db5.appspot.com/o/profile.jpg?alt=media&token=41d6457e-a34f-4dcf-9a3a-da795656c554"
//
//                    refusers.updateChildren(userHashMap).addOnCompleteListener{
//                            task->
//                        if(task.isSuccessful){
//                            val intent=Intent(this,MainActivity::class.java)
//                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
//                            Toast.makeText(this,"User Registered", Toast.LENGTH_SHORT).show()
//                            startActivity(intent)
//                            finish()
//                        }
//                    }
//
//
//                }else{
//                    Toast.makeText(this,"Something went wrong", Toast.LENGTH_SHORT).show()
//                }
//            }

        }

    }

}