package com.example.pet

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.pet.Adapters.PetAdapter
import com.example.pet.ModelClasses.ChatItem
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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var mPets:List<Pet>?=null
    private var refUsers:DatabaseReference?=null
    private var firebaseUser:FirebaseUser?=null


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

        val view=inflater.inflate(R.layout.fragment_home, container, false)


        val myprofile=view.findViewById<ImageView>(R.id.home_mypic)
        val name=view.findViewById<TextView>(R.id.home_welcomeText)

        firebaseUser= FirebaseAuth.getInstance().currentUser
        refUsers= FirebaseDatabase.getInstance().reference.child("user").child(firebaseUser!!.uid)

        refUsers!!.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    val user: User?=snapshot.getValue(User::class.java)
                    name.text="Welcome, "+user?.getUsername()
                    Picasso.get().load(user?.getProfile()).into(myprofile)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        mPets=ArrayList()
        val refUsers= FirebaseDatabase.getInstance().reference.child("pets")
        refUsers.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                (mPets as ArrayList<Pet>)?.clear()
                for(s in snapshot.children){
                    val pet: Pet?=s.getValue(Pet::class.java)
                    (mPets as ArrayList<Pet>)?.add(pet!!)
                }
                Log.d("Pets",mPets.toString())
                val adapter=PetAdapter(requireContext(),mPets!!)
                val postsRecyclerView: RecyclerView = view.findViewById(R.id.home_recyclerView)
                postsRecyclerView.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
                postsRecyclerView.adapter=adapter
            }

            override fun onCancelled(error: DatabaseError) {
               val adapter=PetAdapter(requireContext(),mPets!!)
                val postsRecyclerView: RecyclerView = view.findViewById(R.id.home_recyclerView)
                postsRecyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                postsRecyclerView.adapter=adapter
            }
        })

//

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}