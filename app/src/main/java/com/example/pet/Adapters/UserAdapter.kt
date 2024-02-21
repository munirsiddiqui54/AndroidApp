package com.example.pet.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.pet.ChatActivity
import com.example.pet.MainActivity
import com.example.pet.ModelClasses.ChatItem
import com.example.pet.ModelClasses.User
import com.example.pet.R
import com.squareup.picasso.Picasso

class UserAdapter(mContext:Context,mUsers:List<ChatItem>,isChatChecked:Boolean)
    :RecyclerView.Adapter<UserAdapter.ViewHolder?>()
{
        private var mContext: Context
        private val mUsers:List<ChatItem>
        private val isChatChecked:Boolean

        init {
            this.mContext=mContext
            this.mUsers=mUsers
            this.isChatChecked=isChatChecked
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View=LayoutInflater.from(mContext).inflate(R.layout.user_item,parent,false)
        return UserAdapter.ViewHolder(view,mContext)
    }

    override fun getItemCount(): Int {
        return mUsers.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val user:ChatItem =mUsers[position]
        holder.userNameText.text=user!!.getName()
        holder.id=user!!.getId()
        holder.profile=user!!.getPhoto()
        holder.lastmsg.text=user!!.getLastMsg()
        if(user!!.isSeen()){
            holder.isSeen.visibility=View.GONE
        }
        Picasso.get().load(user!!.getPhoto()).placeholder(R.drawable.myprofile).into(holder.userProfile)
    }
        class ViewHolder(itemView:View,context: Context):RecyclerView.ViewHolder(itemView){

            var userNameText:TextView
            var userProfile:ImageView
            var lastmsg:TextView
            var isSeen:CardView

            var id:String?=null
            var profile:String?=null

            init{
                userNameText=itemView.findViewById(R.id.username)
                userProfile=itemView.findViewById(R.id.profile)
                lastmsg=itemView.findViewById(R.id.last_msg)
                isSeen=itemView.findViewById(R.id.isSeen)

                itemView.setOnClickListener{
                    val intent= Intent(context, ChatActivity::class.java)
                    intent.putExtra("friendName",userNameText.text.toString())
                    intent.putExtra("friendId",id.toString())
                    intent.putExtra("friendProfile",profile.toString())
                    intent.putExtra("isGroup",false)
                    context.startActivity(intent)
                }
            }


        }
}