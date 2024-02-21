package com.example.pet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.pet.ModelClasses.Pet
import com.example.pet.ModelClasses.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso

class PetProfileActivity : AppCompatActivity() {
    var owner:User?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet_profile)

        val pet: Pet? = intent.getSerializableExtra("pet") as? Pet
        val firebaseUser:FirebaseUser?=FirebaseAuth.getInstance().currentUser
        val refUsers= FirebaseDatabase.getInstance().reference.child("user").child(firebaseUser!!.uid)

        var user:User?=null

        refUsers!!.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    user=snapshot.getValue(User::class.java)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


        val profile:ImageView=findViewById(R.id.pets_image)
        val name:TextView=findViewById(R.id.pets_name)
        val age:TextView=findViewById(R.id.pets_age)
        val breed:TextView=findViewById(R.id.pets_breed)
        val color:TextView=findViewById(R.id.pets_color)
        val description:TextView=findViewById(R.id.pets_description)
        val medical:TextView=findViewById(R.id.pets_medical)
        val reason:TextView=findViewById(R.id.pets_reason)
        val ownerName:TextView=findViewById(R.id.pets_owner_name)
        val ownerPhoto:ImageView=findViewById(R.id.pets_owner_photo)

        val gender:TextView=findViewById(R.id.pets_gender)

        var prof:String?=null


        name.text=pet?.getName()
        age.text=pet?.getAge()
        breed.text=pet!!.getBreed()
        color.text=pet!!.getColor()
        description.text=pet!!.getDescription()
        medical.text=pet!!.getMedical()
        gender.text=pet!!.getGender()
        reason.text=pet!!.getReason()

        Picasso.get().load(pet!!.getPhoto()).placeholder(R.drawable.img_6).into(profile)

        val button:Button=findViewById(R.id.contact)
        button.setOnClickListener{
            if(prof!=null){
                val intent= Intent(this, ChatActivity::class.java)
                intent.putExtra("friendName",ownerName.text)
                intent.putExtra("friendId",pet!!.getOwner())
                intent.putExtra("friendProfile",prof)
                startActivity(intent)
            }
        }

        findViewById<ImageView>(R.id.pet_share).setOnClickListener{
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, "\"Meet ${pet!!.getName()} \uD83D\uDC3E, a ${pet!!.getAge()}-old ${pet!!.getBreed()} ${pet!!.getCategory()} looking for a forever home! ${pet!!.getDescription()}. Currently in the care of ${owner!!.getUsername()}, ${pet!!.getName()} is in good health and up-to-date on all medical needs. \uD83C\uDFE5✨ #PetAdoption #PetCompass #RescuePets\"")
            }
            startActivity(Intent.createChooser(shareIntent, "Share Pet via"))
        }

        val reqBtn=findViewById<Button>(R.id.pet_request)
        reqBtn.setOnClickListener{
            val ref:DatabaseReference=FirebaseDatabase.getInstance().reference.child("requests")
            val msgMap=HashMap<String,Any?>()
            val key=ref.push().key
            msgMap["reqKey"]=key
            msgMap["rId"]=user!!.getUid()
            msgMap["rName"]=user!!.getUsername()
            msgMap["rPet"]=pet!!.getName()
            msgMap["rPhoto"]=user!!.getProfile()
            msgMap["rStatus"]="pending"
            msgMap["oId"]=pet!!.getOwner()
            ref.child(key!!).updateChildren(msgMap).addOnCompleteListener{
                    task->
                if(task.isSuccessful){
                    Toast.makeText(this,"Request Sent to ${ownerName.text}",Toast.LENGTH_LONG).show()
                }
            }
        }
        val ref:DatabaseReference=FirebaseDatabase.getInstance().reference.child("user")

        findViewById<LinearLayout>(R.id.pet_showOwner).setOnClickListener{
            val intent= Intent(this, UserProfile::class.java)
            intent.putExtra("uid",pet!!.getOwner().toString())
            startActivity(intent)
        }

        val x=this

        ref.child(pet.getOwner()).addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val user = snapshot.getValue(User::class.java)
                    owner=user
                    ownerName.text=user?.getUsername()
                    prof=user?.getProfile()
                    Picasso.get().load(user?.getProfile()).placeholder(R.drawable.myprofile).into(ownerPhoto)
                } else {
                    // User with the provided ID doesn't exist
                    Toast.makeText(x,"ID doesnt exists" ,Toast.LENGTH_LONG).show()
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(x,"Something w wrong" ,Toast.LENGTH_LONG).show()
            }
        })
    }
}