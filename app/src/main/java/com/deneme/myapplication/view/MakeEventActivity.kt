package com.deneme.myapplication.view

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.deneme.myapplication.R
import com.deneme.myapplication.databinding.ActivityMakeEventBinding
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID


class MakeEventActivity : AppCompatActivity() {

    var selectedImage : Uri? = null
    var selectedBitmap : Bitmap? = null
    private lateinit var storage : FirebaseStorage
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database : FirebaseFirestore

    private lateinit var binding: ActivityMakeEventBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_make_event)

        storage = FirebaseStorage.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseFirestore.getInstance()

        binding = ActivityMakeEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.shareButton.setOnClickListener {
            sharePhoto()
        }

        binding.uploadImageView.setOnClickListener {
            selectPhoto()
        }



    }

    fun sharePhoto(){
        //depoişlemleri
        //UUID->universal unique id
        val uuid = UUID.randomUUID()
        val imageName = "${uuid}.jpg"

        val reference = storage.reference
        val imageReference = reference.child("denemeImages").child(imageName)

        if( selectedImage != null) {
            imageReference.putFile(selectedImage!!).addOnSuccessListener { taskSnapshot ->
                val uploadImageReference = FirebaseStorage.getInstance().reference.child("denemeImages").child(imageName)
                uploadImageReference.downloadUrl.addOnSuccessListener { uri ->
                    val downloadUrl = uri.toString()
                    val currentUserEmail = firebaseAuth.currentUser!!.email.toString()
                    val userComment = binding.TVuserComment.text.toString()
                    val currentUsername = binding.TVusername.text.toString()
                    val shareDateTime = Timestamp.now() //güncel olan tarihi bizim için veriyor

                    if (userComment.isEmpty() && currentUsername.isEmpty()) {
                        Toast.makeText(this,"Please enter your comment and username!",Toast.LENGTH_SHORT).show()

                        return@addOnSuccessListener

                    } else {
                        Toast.makeText(this,"Succesfull!",Toast.LENGTH_SHORT).show()
                    }

                    //veritabanı işlemleri

                    val postHashMap = hashMapOf<String, Any>()
                    postHashMap.put("imageurl",downloadUrl)
                    postHashMap.put("useremail",currentUserEmail)
                    postHashMap.put("userComment",userComment)
                    postHashMap.put("currentUsername",currentUsername)
                    postHashMap.put("date",shareDateTime)

                    database.collection("Post").add(postHashMap).addOnCompleteListener { task ->
                        if(task.isSuccessful) {
                            finish()
                        }
                    }.addOnFailureListener { exception ->
                        Toast.makeText(applicationContext,exception.localizedMessage,Toast.LENGTH_LONG).show()
                    }

                }.addOnFailureListener { exception ->
                    Toast.makeText(applicationContext,exception.localizedMessage,Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    fun selectPhoto(){
        if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            //izin alınmamış
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),1)
        } else {
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent,2)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 1){
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                // izin verilince yapılacaklar
                val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(galleryIntent,2)
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if ( requestCode == 2 && resultCode == Activity.RESULT_OK && data != null){

            selectedImage = data.data

            if(selectedImage != null ) {

                if (Build.VERSION.SDK_INT >= 28 ){

                    val source = ImageDecoder.createSource(this.contentResolver,selectedImage!!)
                    selectedBitmap = ImageDecoder.decodeBitmap(source)
                    binding.uploadImageView.setImageBitmap(selectedBitmap)

                } else {

                    selectedBitmap = MediaStore.Images.Media.getBitmap(this.contentResolver,selectedImage)
                    binding.uploadImageView.setImageBitmap(selectedBitmap)
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

}