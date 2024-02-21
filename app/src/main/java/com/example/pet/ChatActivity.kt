package com.example.pet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pet.Adapters.ChatAdapter
import com.example.pet.ModelClasses.Chat
import com.example.pet.ModelClasses.Community
import com.example.pet.ModelClasses.Pet
import com.example.pet.ModelClasses.User
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
    var name:String?=null
    var friendProfile:String?=null
    var username:String=""
    var myprofile:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        firebaseUser= FirebaseAuth.getInstance().currentUser

        val bundle: Bundle?=intent.extras





        val isGroup=bundle!!.getBoolean("isGroup")
        if(!isGroup){
            findViewById<LinearLayout>(R.id.banner).setOnClickListener{
                val intent= Intent(this, UserProfile::class.java)
                intent.putExtra("uid",bundle!!.getString("friendId"))
                startActivity(intent)
            }

        }
        var id:String?
        if(isGroup){
            val com: Community? = intent.getSerializableExtra("friend") as? Community

            name=com!!.getName()
            friendProfile=com!!.getProfile()
            id=com!!.getId()
        }else{
            name=bundle!!.getString("friendName")
            id=bundle!!.getString("friendId")
            friendProfile=bundle!!.getString("friendProfile")

        }

        Log.d("BUN",friendProfile!!)
        Log.d("BUN",id!!)
        Log.d("BUN",name!!)

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

        sendBtn.setOnClickListener{
            val message=editText.text.toString()
            if(message==""){
                Toast.makeText(this,"Please write a message first",Toast.LENGTH_SHORT).show()
            }else{
                sendMessage(firebaseUser!!.uid,id,message,isGroup)
            }
            editText.setText("")
        }
        val firebaseUser= FirebaseAuth.getInstance().currentUser
        val refUsers= FirebaseDatabase.getInstance().reference.child("user").child(firebaseUser!!.uid)
        refUsers.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val user= snapshot.getValue(User::class.java)
                    username=user!!.getUsername()
                    myprofile=user!!.getProfile()
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        fun retriveMessage(sender: String, receiver: String?,isGroup: Boolean) {

            mChatList=ArrayList()
            val ref=FirebaseDatabase.getInstance().reference.child("Chats")
val x=this
            ref.addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    (mChatList as ArrayList<Chat>).clear()
                    for(s in snapshot.children){
                        val chat=s.getValue(Chat::class.java)
                        if(isGroup){
                            if (chat!!.getReceiver()==receiver){
                                (mChatList as ArrayList<Chat>).add(chat)
                            }
                        }else{
                            if (chat!!.getSender()==sender && chat.getReceiver()==receiver || chat.getSender()==receiver && chat.getReceiver()==sender){
                                (mChatList as ArrayList<Chat>).add(chat)
                            }
                        }
                    }
                    val adapter=ChatAdapter(this@ChatActivity,mChatList!!)
                    recycleChat!!.adapter=adapter
                   if(!isGroup){
                       val refUsers= FirebaseDatabase.getInstance().reference.child("ChatList").child(sender).child(receiver!!)

                       refUsers.addListenerForSingleValueEvent(object:ValueEventListener{
                           override fun onDataChange(snapshot: DataSnapshot) {
                               if(snapshot.exists()){
                                   val map=HashMap<String,Any>()
                                   map["seen"]=true
                                   refUsers.updateChildren(map)
                               }
                           }
                           override fun onCancelled(error: DatabaseError) {
                               TODO("Not yet implemented")
                           }
                       })
                   }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }

        retriveMessage(firebaseUser!!.uid,id,isGroup)
    }



    private fun sendMessage(sender: String, receiver: String?, message: String,isGroup:Boolean) {
        val ref=FirebaseDatabase.getInstance().reference
        val msgKey=ref.push().key


        val msgMap=HashMap<String,Any?>()
        msgMap["sender"]=sender
        msgMap["receiver"]=receiver
        msgMap["message"]=message
        msgMap["isSeen"]=false
        msgMap["url"]=""
        msgMap["msgId"]=msgKey
        msgMap["rName"]=username
        msgMap["rPhoto"]=myprofile

        ref.child("Chats").child(msgKey!!).setValue(msgMap).addOnCompleteListener{task->
            if(task.isSuccessful && !isGroup ){
                val chatListRef=FirebaseDatabase.getInstance().reference.child("ChatList").child(firebaseUser!!.uid)
                    .child(receiver!!)
                chatListRef.addListenerForSingleValueEvent(object:ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if(!snapshot.exists()){
                            val map=HashMap<String,Any>()
                            map["id"]=receiver
                            map["photo"]=friendProfile!!
                            map["lastMsg"]=message
                            map["seen"]=true
                            map["name"]=name!!
                            chatListRef.setValue(map)
                        }
                        val map=HashMap<String,Any>()
                        map["id"]=sender
                        map["photo"]=myprofile!!
                        map["lastMsg"]=message
                        map["seen"]=false
                        map["name"]=username!!

                        val recRef=FirebaseDatabase.getInstance().reference.child("ChatList").child(receiver!!)
                            .child(firebaseUser!!.uid)
                        recRef.setValue(map)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })

            }
        }

    }
}