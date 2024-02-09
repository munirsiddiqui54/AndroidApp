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
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class Register : AppCompatActivity() {
    private lateinit var myauth:FirebaseAuth
    private lateinit var refusers:DatabaseReference
    private var fbId:String=""
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

        myauth=FirebaseAuth.getInstance()

        login.setOnClickListener{
            val pairs: Array<Pair<View, String>?> = arrayOfNulls(1)
            pairs[0]= Pair<View,String>(kitten,"kitten")
            var op: ActivityOptions?=null
            op= ActivityOptions.makeSceneTransitionAnimation(this,*pairs)
            startActivity(Intent(this, com.example.pet.login::class.java),op.toBundle())
        }

        registerbtn.setOnClickListener{

            registerUser()

        }
    }
    private fun registerUser(){
        val email:String=findViewById<TextInputEditText>(R.id.Remail).text.toString()
        val username:String=findViewById<TextInputEditText>(R.id.Rusername).text.toString()
        val password:String=findViewById<TextInputEditText>(R.id.Rpassword).text.toString()


        if(email.equals("")){
            Toast.makeText(this,"Please Provide email",Toast.LENGTH_SHORT).show()
        }
        else if(username.equals("")){
            Toast.makeText(this,"Please Provide username",Toast.LENGTH_SHORT).show()
        }else if(password.equals("")){
            Toast.makeText(this,"Please Provide password",Toast.LENGTH_SHORT).show()
        }else{
            myauth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{task->
                if(task.isSuccessful){
                    fbId=myauth.currentUser!!.uid
                    refusers=FirebaseDatabase.getInstance().reference.child("user").child(fbId)
                    val userHashMap=HashMap<String,Any>()
                    userHashMap["uid"]=fbId
                    userHashMap["username"]=username
                    userHashMap["email"]=email
                    userHashMap["address"]=""
                    userHashMap["phone"]=""
                    userHashMap["profile"]="https://firebasestorage.googleapis.com/v0/b/petcompass-b0db5.appspot.com/o/profile.jpg?alt=media&token=41d6457e-a34f-4dcf-9a3a-da795656c554"

                    refusers.updateChildren(userHashMap).addOnCompleteListener{
                        task->
                        if(task.isSuccessful){
                            val intent=Intent(this,MainActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            Toast.makeText(this,"User Registered",Toast.LENGTH_SHORT).show()
                            startActivity(intent)
                            finish()
                        }
                    }


                }else{
                    Toast.makeText(this,task.exception?.message.toString(),Toast.LENGTH_SHORT).show()
                }
            }

        }

    }

}