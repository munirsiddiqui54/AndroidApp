package com.example.pet

import android.content.Intent
import android.os.Bundle
import android.support.annotation.MainThread
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.pet.Adapters.User2Adapter
import com.example.pet.Adapters.UserAdapter
import com.example.pet.ModelClasses.ChatItem
import com.example.pet.ModelClasses.Community
import com.example.pet.ModelClasses.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MessagesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MessagesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var userAdapter:UserAdapter?=null
    private var userAdapter2:User2Adapter?=null
    private var mUsers:List<ChatItem>?=null
    private var mCom:List<Community>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View= inflater.inflate(R.layout.fragment_messages, container, false)

        mUsers=ArrayList()
        mCom=ArrayList()

        retriveAllUsers(view)

        val showBtn=view.findViewById<CardView>(R.id.showCreate)
        showBtn.setOnClickListener {
            val intent=Intent(context,CreateCommunity::class.java)
            startActivity(intent)
        }

        return view
    }

    private fun retriveAllUsers(view:View) {
        var firebaseUserID=FirebaseAuth.getInstance().currentUser!!.uid
        val refUsers= FirebaseDatabase.getInstance().reference.child("ChatList").child(firebaseUserID)
         refUsers.addValueEventListener(object : ValueEventListener {
             override fun onDataChange(snapshot: DataSnapshot) {
                 (mUsers as ArrayList<ChatItem>).clear()
                 for(x in snapshot.children){
                     val user:ChatItem?=x.getValue(ChatItem::class.java)
                     (mUsers as ArrayList<ChatItem>).add(user!!)
                 }
//                 Log.d("Message",mUser)
                 var recyclerView:RecyclerView=view.findViewById(R.id.recyclerView)

                 val layoutManager= LinearLayoutManager(requireContext())

                 layoutManager.orientation= LinearLayoutManager.VERTICAL

                 recyclerView.layoutManager=layoutManager
                 userAdapter= UserAdapter(requireContext(),mUsers!!,false)

                 recyclerView.adapter=userAdapter

             }

             override fun onCancelled(error: DatabaseError) {
//                 TODO("Not yet implemented")
             }
        })

        val ref= FirebaseDatabase.getInstance().reference.child("community")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                (mCom as ArrayList<Community>).clear()
                for(s in snapshot.children){
                    val user:Community?=s.getValue(Community::class.java)
                    (mCom as ArrayList<Community>).add(user!!)
                }
//
                var recyclerView2:RecyclerView=view.findViewById(R.id.recyclerViewTop)

                val layoutManager2=LinearLayoutManager(requireContext())

                layoutManager2.orientation=LinearLayoutManager.HORIZONTAL

                recyclerView2.layoutManager=layoutManager2


                userAdapter2=User2Adapter(requireContext(),mCom!!,false)

                Log.d("Message2",mUsers.toString())
                recyclerView2.adapter=userAdapter2

            }

            override fun onCancelled(error: DatabaseError) {
//                 TODO("Not yet implemented")
            }
        })

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MessagesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MessagesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}