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
import com.example.pet.ModelClasses.Community
import com.example.pet.ModelClasses.User
import com.example.pet.R
import com.squareup.picasso.Picasso

class User2Adapter(mContext:Context,mUsers:List<Community>,isChatChecked:Boolean)
    :RecyclerView.Adapter<User2Adapter.ViewHolder?>()
{
    private val mContext: Context
    private val mUsers:List<Community>
    private val isChatChecked:Boolean
    init {
        this.mContext=mContext
        this.mUsers=mUsers
        this.isChatChecked=isChatChecked
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View=LayoutInflater.from(mContext).inflate(R.layout.short_user,parent,false)
        return User2Adapter.ViewHolder(view,mContext)
    }

    override fun getItemCount(): Int {
        return mUsers.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user:Community =mUsers[position]
        holder.userNameText.text=user!!.getName()

        holder.frd=user!!
        Picasso.get().load(user.getProfile()).placeholder(R.drawable.myprofile).into(holder.userProfile)
    }
    class ViewHolder(itemView:View,context:Context):RecyclerView.ViewHolder(itemView){
        var userNameText:TextView
        var userProfile:ImageView
        var frd:Community?=null
        init{
            userNameText=itemView.findViewById(R.id.username2)
            userProfile=itemView.findViewById(R.id.profile2)
            itemView.setOnClickListener{
                val intent= Intent(context, ChatActivity::class.java)
                intent.putExtra("friend",frd)
                intent.putExtra("isGroup",true)
                context.startActivity(intent)
            }
        }
    }
}