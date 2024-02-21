package com.example.pet

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso

class CreateCommunity : AppCompatActivity() {

    var imageUri: Uri?=null
    var storageRef:StorageReference?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_community)
val name=findViewById<TextView>(R.id.community_name)
        val image=findViewById<ImageView>(R.id.com_create)
        val btn=findViewById<Button>(R.id.createBtn)
        storageRef= FirebaseStorage.getInstance().reference.child("Community")

        var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode== Activity.RESULT_OK) {
//                result.resultCode
                // There are no request codes
                val data: Intent? = result.data
                imageUri=data!!.data
                Picasso.get().load(imageUri).into(image)
            }
        }

        image.setOnClickListener{
            val intent= Intent()
            intent.type="image/*"
            intent.action= Intent.ACTION_GET_CONTENT
            resultLauncher.launch(intent)
        }

        btn.setOnClickListener{
            if(name.text.toString()==""){
                Toast.makeText(this,"Please Name your Pet Community", Toast.LENGTH_LONG).show()
            }
            else if(imageUri==null){
                Toast.makeText(this,"Please Upload a Profile Picture ", Toast.LENGTH_LONG).show()
            }
            else{
                uploadPic(imageUri!!)
            }
        }
    }

    private fun uploadPic(imageUri: Uri) {
        val progressBar= ProgressDialog(this)
        progressBar.setMessage("Creating Community")
        progressBar.show()
        if(imageUri!=null){
            val fileRef=storageRef!!.child(System.currentTimeMillis().toString()+".jpg")
            fileRef.putFile(imageUri!!).addOnSuccessListener { task ->
                // Get download URL from task snapshot
                task.storage.downloadUrl.addOnSuccessListener { downloadUrl ->
                    // URL of the uploaded image
                    val imageUrl = downloadUrl.toString()

                    val ref= FirebaseDatabase.getInstance().reference.child("community")
                    val name=findViewById<EditText>(R.id.community_name)

                    val key= ref.push().key

                    val msgMap=HashMap<String,Any?>()
                    msgMap["name"]=name.text.toString()
                    msgMap["profile"]=imageUrl
                    msgMap["id"]=key


                    ref
                        .child(key!!)
                        .updateChildren(msgMap)
                        .addOnCompleteListener{
                                task->
                            if(task.isSuccessful){
                                Toast.makeText(this,"${name.text} has been created",Toast.LENGTH_LONG).show()
                                progressBar.dismiss()
                                finish()
                            }
                        }
                }
            }
        }
    }
}