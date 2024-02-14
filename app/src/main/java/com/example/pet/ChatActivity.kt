package com.example.pet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pet.Adapters.ChatAdapter
import com.example.pet.ModelClasses.Chat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import org.w3c.dom.Text

class ChatActivity : AppCompatActivity() {
    var firebaseUser:FirebaseUser?=null
    var mChatList:ArrayList<Chat>?=null
    var recycleChat:RecyclerView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        firebaseUser= FirebaseAuth.getInstance().currentUser

        val bundle: Bundle?=intent.extras
        val name=bundle!!.getString("friendName")
        val id=bundle!!.getString("friendId")

        val friendProfile=bundle!!.getString("friendProfile")
        val img:ImageView=findViewById(R.id.friendProfile)
        val editText:EditText=findViewById(R.id.message)
        val sendBtn:ImageView=findViewById(R.id.send)
        recycleChat=findViewById(R.id.chats)

        val layoutManager= LinearLayoutManager(this)
        layoutManager.orientation= LinearLayoutManager.VERTICAL


        recycleChat!!.layoutManager=layoutManager

        Picasso.get().load(friendProfile).placeholder(R.drawable.myprofile).into(img)

        val text=findViewById<TextView>(R.id.friendName)
        text.text=name

        retriveMessage(firebaseUser!!.uid,id)
        sendBtn.setOnClickListener{
            val message=editText.text.toString()
            if(message==""){
                Toast.makeText(this,"Please write a message first",Toast.LENGTH_SHORT).show()
            }else{
                sendMessage(firebaseUser!!.uid,id,message)
            }
            editText.setText("")
        }

    }

    private fun retriveMessage(sender: String, receiver: String?) {


        mChatList=ArrayList()
        val ref=FirebaseDatabase.getInstance().reference.child("Chats")

        ref.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                (mChatList as ArrayList<Chat>).clear()
                for(s in snapshot.children){
                    val chat=s.getValue(Chat::class.java)
                    if (chat!!.getSender()==sender && chat.getReceiver()==receiver || chat.getSender()==receiver && chat.getReceiver()==sender){
                        (mChatList as ArrayList<Chat>).add(chat)
                    }
                }
                val adapter=ChatAdapter(this@ChatActivity,mChatList!!)
                recycleChat!!.adapter=adapter
            }


            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun sendMessage(sender: String, receiver: String?, message: String) {
        val ref=FirebaseDatabase.getInstance().reference
        val msgKey=ref.push().key

        val msgMap=HashMap<String,Any?>()
        msgMap["sender"]=sender
        msgMap["receiver"]=receiver
        msgMap["message"]=message
        msgMap["isSeen"]=false
        msgMap["url"]=""
        msgMap["msgId"]=msgKey

        ref.child("Chats").child(msgKey!!).setValue(msgMap).addOnCompleteListener{task->
            if(task.isSuccessful){
                val chatListRef=FirebaseDatabase.getInstance().reference.child("ChatList")

            }
        }

    }
}