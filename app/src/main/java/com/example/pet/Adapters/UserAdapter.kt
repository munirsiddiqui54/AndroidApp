package com.example.pet.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.pet.ChatActivity
import com.example.pet.MainActivity
import com.example.pet.ModelClasses.User
import com.example.pet.R
import com.squareup.picasso.Picasso

class UserAdapter(mContext:Context,mUsers:List<User>,isChatChecked:Boolean)
    :RecyclerView.Adapter<UserAdapter.ViewHolder?>()
{
        private var mContext: Context
        private val mUsers:List<User>
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
    val user:User =mUsers[position]
        holder.userNameText.text=user!!.getUsername()
        holder.id=user!!.getUid()
        holder.profile=user!!.getProfile()
        Picasso.get().load(user.getProfile()).placeholder(R.drawable.myprofile).into(holder.userProfile)
    }
        class ViewHolder(itemView:View,context: Context):RecyclerView.ViewHolder(itemView){

            var userNameText:TextView
            var userProfile:ImageView
            var lastmsg:TextView
            var id:String?=null
            var profile:String?=null
            init{
                userNameText=itemView.findViewById(R.id.username)
                userProfile=itemView.findViewById(R.id.profile)
                lastmsg=itemView.findViewById(R.id.last_msg)

                itemView.setOnClickListener{
                    val intent= Intent(context, ChatActivity::class.java)
                    intent.putExtra("friendName",userNameText.text.toString())
                    intent.putExtra("friendId",id.toString())
                    intent.putExtra("friendProfile",profile.toString())
                    context.startActivity(intent)
                }
            }


        }
}