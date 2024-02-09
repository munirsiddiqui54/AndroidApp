package com.example.pet

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.squareup.picasso.Picasso;
import com.example.pet.ModelClasses.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var refUsers: DatabaseReference?=null
    var firebaseUser: FirebaseUser?=null
    private val RequestCode=438

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)


//            refUsers!!.addValueEventListener(object : ValueEventListener {
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    if(snapshot.exists()){
//                        val user: User?=snapshot.getValue(User::class.java)
//                        val myusername=view?.findViewById<TextView>(R.id.myusername)
////                        val myusername=findvie
////                        Log.d("MainActivity", user!!.toString())
////                        Toast.makeText(this@ProfileFragment, "done", Toast.LENGTH_SHORT).show()
//                        myusername?.text=user!!.getUsername()
//                    }
//                }
//
//                override fun onCancelled(error: DatabaseError) {
////                    Toast.makeText(this@ProfileFragment, "Some Thing Went WWWW", Toast.LENGTH_SHORT).show()
//                }
//            })


        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        firebaseUser= FirebaseAuth.getInstance().currentUser
        refUsers= FirebaseDatabase.getInstance().reference.child("user").child(firebaseUser!!.uid)
        val view:View= inflater.inflate(R.layout.fragment_profile, container, false)
        val myusername=view!!.findViewById<TextView>(R.id.myusername)
        val myemail=view!!.findViewById<EditText>(R.id.myemail)
        val myprofile=view!!.findViewById<ImageView>(R.id.myprofile)
        val myaddress=view!!.findViewById<EditText>(R.id.myaddress)
        val myphone=view!!.findViewById<EditText>(R.id.myphone)
        val updateBtn=view!!.findViewById<Button>(R.id.updateBtn)
        updateBtn.setOnClickListener{
            //update
        }

        myprofile.setOnClickListener {
            val intent=Intent()
            intent.type="image/*"
            intent.action=Intent.ACTION_GET_CONTENT
            startActivityForResult(intent,RequestCode)

        }
        // Inflate the layout for this fragment
        refUsers?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val user: User?=snapshot.getValue(User::class.java)

                    myusername!!.text=user!!.getUsername()
                    myemail!!.setText(user!!.getEmail())
                    myaddress!!.setText(user!!.getAddress())
                    myphone!!.setText(user!!.getPhone())
                    Picasso.get().load(user?.getProfile()).into(myprofile)

                }
            }

            override fun onCancelled(error: DatabaseError) {
//                    Toast.makeText(this@ProfileFragment, "Some Thing Went WWWW", Toast.LENGTH_SHORT).show()
            }
        })

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}