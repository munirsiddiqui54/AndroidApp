package com.example.pet.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pet.ChatActivity
import com.example.pet.ModelClasses.Chat
import com.example.pet.ModelClasses.User
import com.example.pet.R
import com.example.pet.UserProfile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class ChatAdapter(mContext: Context, mChats:ArrayList<Chat>)
    : RecyclerView.Adapter<ChatAdapter.ViewHolder?>() {

    private var mContext: Context
    private val mChats:ArrayList<Chat>
    init {
        this.mContext=mContext
        this.mChats=mChats
    }
    class ViewHolder(itemView: View, context: Context):RecyclerView.ViewHolder(itemView){

        var message: TextView
        var rName:TextView
        var rPhoto:ImageView
        var id:String?=null
        init{
            message=itemView.findViewById(R.id.message)
            rName=itemView.findViewById(R.id.r_name)
            rPhoto=itemView.findViewById(R.id.r_photo)
            rPhoto.setOnClickListener{
                val intent= Intent(context, UserProfile::class.java)
                intent.putExtra("uid",id.toString())
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val chat:Chat=mChats[]
        var view:View
        if(viewType==1){
            view= LayoutInflater.from(mContext).inflate(R.layout.chat_item_right,parent,false)
        }else{
            view= LayoutInflater.from(mContext).inflate(R.layout.chat_item_left,parent,false)
        }
        return ChatAdapter.ViewHolder(view,mContext)

    }
    override fun getItemCount(): Int {
       return mChats.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val chat:Chat =mChats[position]
        holder.message.text=chat!!.getMessage()
        holder.rName.text=chat!!.getRName()
        holder.id=chat!!.getSender()
        if (chat?.getRPhoto()!=""){
                Picasso.get().load(chat!!.getRPhoto()).placeholder(R.drawable.myprofile).into(holder.rPhoto)
            }

    }

    override fun getItemViewType(position: Int): Int {
         val firebaseUser=FirebaseAuth.getInstance().currentUser
        return if(mChats[position].getSender()==firebaseUser!!.uid){
            1
        }else{
            0
        }
    }
}