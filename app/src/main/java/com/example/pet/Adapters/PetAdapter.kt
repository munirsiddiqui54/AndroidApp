package com.example.pet.Adapters

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.pet.ChatActivity
import com.example.pet.ModelClasses.Pet
import com.example.pet.PetProfileActivity
import com.example.pet.R
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso
import java.io.File
import android.graphics.Bitmap
import androidx.core.content.FileProvider
import java.io.FileOutputStream
import java.net.URL
import java.net.URLDecoder

class PetAdapter(mContext: Context, mPets:List<Pet>)
    : RecyclerView.Adapter<PetAdapter.ViewHolder?>()
{
    private var mContext: Context
    private val mPets:List<Pet>

    init {
        this.mContext=mContext
        this.mPets=mPets
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(mContext).inflate(R.layout.pet_item,parent,false)
        return PetAdapter.ViewHolder(view,mContext)
    }

    override fun getItemCount(): Int {
        return mPets.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val user: Pet =mUsers[position]
//        holder.userNameText.text=user!!.getUsername()
//        holder.id=user!!.getUid()
//        holder.profile=user!!.getProfile()
        val pet:Pet=mPets[position]
        holder.petName.text=pet!!.getName()
        holder.bind(pet)
        holder.tv.text=pet!!.getDescription()
        if(pet.getPhoto()!=""){
            Picasso.get().load(pet.getPhoto()).placeholder(R.drawable.img_6).into(holder.petImage)
        }
    }
    class ViewHolder(itemView: View, context: Context): RecyclerView.ViewHolder(itemView){

        var petImage:ImageView
var petName:TextView
var currentPet:Pet?=null
        var tv:TextView
        fun bind(pet: Pet) {
            currentPet = pet
        }
        init{
            petImage=itemView.findViewById(R.id.pet_view_image)
    petName=itemView.findViewById(R.id.pet_name)
            tv=itemView.findViewById(R.id.pet_description)


            itemView.findViewById<Button>(R.id.pet_button).setOnClickListener{
                val intent= Intent(context, PetProfileActivity::class.java)
                intent.putExtra("pet",currentPet)
                context.startActivity(intent)
            }


            itemView.setOnClickListener{
                val intent= Intent(context, PetProfileActivity::class.java)
                intent.putExtra("pet",currentPet)
                context.startActivity(intent)
            }

        }



    }
}