package com.example.pet

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.squareup.picasso.Picasso
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask

class AddPet : AppCompatActivity() {

    private lateinit var myauth:FirebaseAuth
    private lateinit var refusers:DatabaseReference
    var storageRef:StorageReference?=null
    var fbId:String=""
    var imageUri:Uri?=null

    private fun uploadPic(img: Uri?) {
        val progressBar= ProgressDialog(this)
        progressBar.setMessage("Posting your Pet...")
        progressBar.show()
        if(img!==null){
            val fileRef=storageRef!!.child(System.currentTimeMillis().toString()+".jpg")

            fileRef.putFile(img!!).addOnSuccessListener {task ->
                // Get download URL from task snapshot
                task.storage.downloadUrl.addOnSuccessListener { downloadUrl ->
                    // URL of the uploaded image
                    val imageUrl = downloadUrl.toString()

                    fbId=myauth.currentUser!!.uid

                    val name =findViewById<EditText>(R.id.pet_name)
                    val age=findViewById<EditText>(R.id.pet_age)
                    val breed =findViewById<EditText>(R.id.pet_breed)
                    val category=findViewById<EditText>(R.id.pet_category)
                    val color =findViewById<EditText>(R.id.pet_color)
                    val description=findViewById<EditText>(R.id.pet_description)
                    val medical =findViewById<EditText>(R.id.pet_medical)
                    val reason=findViewById<EditText>(R.id.pet_reason)


                    val male =findViewById<RadioButton>(R.id.radio_male)

                    val key= refusers.push().key
                    val msgMap=HashMap<String,Any?>()
                    msgMap["id"]=key
                    msgMap["name"]=name.text.toString()
                    msgMap["age"]=age.text.toString()
                    msgMap["breed"]=breed.text.toString()
                    msgMap["category"]=category.text.toString()
                    msgMap["color"]=color.text.toString()
                    msgMap["medical"]=medical.text.toString()
                    msgMap["reason"]=reason.text.toString()
                    msgMap["description"]=description.text.toString()
                    msgMap["photo"]=imageUrl
                    msgMap["owner"]=fbId

                    val selectedGender = if (male.isChecked) {
                        "Male"
                    } else {
                        "Female"
                    }
                    msgMap["gender"]=selectedGender

                    refusers
                        .child(key!!)
                        .updateChildren(msgMap)
                        .addOnCompleteListener{
                                task->
                            if(task.isSuccessful){
                                Toast.makeText(this,"Your Pet has been Successfully Posted",Toast.LENGTH_LONG).show()
                                finish()
                            }
                        }
                }
                    .addOnFailureListener { exception ->
                        // Handle failure to retrieve download URL
                        Log.e("FirebaseStorage", "Failed to retrieve download URL", exception)
                    }
                    .addOnCompleteListener {
                        // This method is called regardless of success or failure
                        progressBar.dismiss()
                    }
            }.addOnFailureListener {
                // this method is called when there is failure in file upload.
                // in this case we are dismissing the dialog and displaying toast message
                progressBar.dismiss()
                Toast.makeText(this, "Fail to Upload Image..", Toast.LENGTH_SHORT)
                    .show()
                Log.e("FirebaseStorage", "Upload failed", it)
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_pet)
        val name =findViewById<EditText>(R.id.pet_name)
        val age=findViewById<EditText>(R.id.pet_age)
        val medical =findViewById<EditText>(R.id.pet_medical)
        val reason=findViewById<EditText>(R.id.pet_reason)
        val image=findViewById<ImageView>(R.id.pet_image)

        myauth=FirebaseAuth.getInstance()
        storageRef= FirebaseStorage.getInstance().reference.child("PetProfiles")
        fbId=myauth.currentUser!!.uid

        refusers=FirebaseDatabase.getInstance().reference.child("pets")



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
            val intent=Intent()
            intent.type="image/*"
            intent.action=Intent.ACTION_GET_CONTENT
            resultLauncher.launch(intent)
        }

        val fileRef=storageRef!!.child(System.currentTimeMillis().toString()+".jpg")


        val addBtnn=findViewById<Button>(R.id.addMyPet)
        addBtnn.setOnClickListener{
            if(name.text.toString()=="" || age.text.toString()=="" || medical.text.toString()=="" ||reason.text.toString()==""){
                Toast.makeText(this,"Please Provide required details",Toast.LENGTH_LONG).show()
            }
            else if(imageUri==null){
                Toast.makeText(this,"Please Upload a Picture of your pet",Toast.LENGTH_LONG).show()
            }
            else{
               uploadPic(imageUri)
            }
            }
        }
    }

