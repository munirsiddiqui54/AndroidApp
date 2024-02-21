package com.example.pet

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
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
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import com.squareup.picasso.Picasso;
import com.example.pet.ModelClasses.User
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.UploadTask.TaskSnapshot

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
    var storageRef:StorageReference?=null
    private var imageUri:Uri?=null
    var firebaseUser: FirebaseUser?=null
    private val RequestCode=438

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)



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
        storageRef=FirebaseStorage.getInstance().reference.child("userProfiles")

        val view:View= inflater.inflate(R.layout.fragment_profile, container, false)
        val myusername=view!!.findViewById<TextView>(R.id.myusername)
        val myusername2=view!!.findViewById<TextView>(R.id.myusername2)
        val myemail=view!!.findViewById<TextView>(R.id.myemail)
        val myprofile=view!!.findViewById<ImageView>(R.id.myprofile)
        val myaddress=view!!.findViewById<EditText>(R.id.myaddress)
        val myphone=view!!.findViewById<EditText>(R.id.myphone)
        val updateBtn=view!!.findViewById<Button>(R.id.updateBtn)
        updateBtn.setOnClickListener{
            //update
            val progressBar= ProgressDialog(context)
            progressBar.setMessage("Updating Profile...")
            progressBar.show()
            val map = HashMap<String, Any>()
            map["username"] = myusername2.text.toString()
           map["phone"]=myphone.text.toString()
            map["address"]=myaddress.text.toString()
            refUsers!!.updateChildren(map).addOnCompleteListener{
                progressBar.dismiss()
                Toast.makeText(context, "Profile Updated...", Toast.LENGTH_LONG)
                    .show()
            }
        }


        fun uploadPic(img:Uri?){
            val progressBar= ProgressDialog(context)
            progressBar.setMessage("Profile Pic is Uploading...")
            progressBar.show()
            if(img!==null){
                val fileRef=storageRef!!.child(System.currentTimeMillis().toString()+".jpg")
                var uploadTask:StorageTask<*>
//                uploadTask=fileRef.putFile(imageUri!!)
//                uploadTask.continueWithTask {  }
                fileRef.putFile(img!!).addOnSuccessListener {task ->
                    // Get download URL from task snapshot
                    task.storage.downloadUrl.addOnSuccessListener { downloadUrl ->
                        // URL of the uploaded image
                        val imageUrl = downloadUrl.toString()
                        // Proceed with your logic here, such as updating the user's profile with the image URL
                        val map = HashMap<String, Any>()
                        map["profile"] = imageUrl
                        // Update user's profile with image URL
                        refUsers!!.updateChildren(map)
                    }
                        .addOnFailureListener { exception ->
                            // Handle failure to retrieve download URL
                            Log.e("FirebaseStorage", "Failed to retrieve download URL", exception)
                        }
                        .addOnCompleteListener {
                            // This method is called regardless of success or failure
                            progressBar.dismiss()
                        }
                }.addOnFailureListener {
                    // this method is called when there is failure in file upload.
                    // in this case we are dismissing the dialog and displaying toast message
                    progressBar.dismiss()
                    Toast.makeText(context, "Fail to Upload Image..", Toast.LENGTH_SHORT)
                        .show()
                    Log.e("FirebaseStorage", "Upload failed", it)
                }

            }
        }
        var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode== Activity.RESULT_OK) {
//                result.resultCode
                // There are no request codes
                val data: Intent? = result.data
                imageUri=data!!.data
                uploadPic(imageUri)
            }
        }

        myprofile.setOnClickListener {
            val intent=Intent()
            intent.type="image/*"
            intent.action=Intent.ACTION_GET_CONTENT
            resultLauncher.launch(intent)
        }
        view.findViewById<ImageView>(R.id.logoutBtn).setOnClickListener{
            signOut()
        }

        // Inflate the layout for this fragment
        refUsers?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val user: User?=snapshot.getValue(User::class.java)

                    myusername2!!.text=user!!.getUsername()
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
    private fun signOut() {
        val firebaseAuth:FirebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signOut()

            startActivity(Intent(requireContext(), login::class.java))
            requireActivity().finish()
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