package com.example.pet.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.pet.R
import com.example.pet.ModelClasses.Notification
import com.example.pet.UserProfile
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso


class NotificationAdapter(private val mContext: Context, private val mNotifications: List<Notification>) :
    RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(mContext).inflate(R.layout.notify_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val notification: Notification = mNotifications[position]
        // Set data to views
        holder.textViewHeader1.text = notification.getName()+" is Interested in Adopting Your ${notification.getPet()}"
//        holder.textViewHeader2.text = notification.getRName()
        holder.id=notification.getId()
        Picasso.get().load(notification.getPhoto()).placeholder(R.drawable.myprofile).into(holder.imageViewProfile)
        holder.buttonAccept.setOnClickListener {
            // Handle accept button click
            foo(notification.getKey(),"Accept")
        }

        holder.buttonDecline.setOnClickListener {
            // Handle decline button click
            foo(notification.getKey(),"Decline")
        }
    }

    private fun foo(key:String?,x:String) {

        val refUsers= FirebaseDatabase.getInstance().reference.child("requests")
//        val mUsers=ArrayList()
        val map=HashMap<String,Any?>()
        map["rStatus"]=x
        refUsers.child(key!!).updateChildren(map).addOnCompleteListener{
            //implement
            Toast.makeText(mContext,"Request "+x+"ed",Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return mNotifications.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewHeader1: TextView= itemView.findViewById(R.id.r_h1)
        val textViewHeader2: TextView = itemView.findViewById(R.id.r_h2)
        val imageViewProfile: ImageView = itemView.findViewById(R.id.r_profile)
        val buttonAccept: Button = itemView.findViewById(R.id.r_accept)
        val buttonDecline: Button = itemView.findViewById(R.id.r_decline)
        var id:String?=null
        init{
            itemView.setOnClickListener{
                val intent= Intent(mContext, UserProfile::class.java)
                intent.putExtra("uid",id)
                mContext.startActivity(intent)
            }
        }

    }
}