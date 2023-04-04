package com.deneme.myapplication.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.deneme.myapplication.model.PostData
import com.deneme.myapplication.R
import com.deneme.myapplication.adapter.HomeRecyclerAdapter
import com.deneme.myapplication.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database : FirebaseFirestore
    private lateinit var recyclerViewAdapter : HomeRecyclerAdapter

    var postsList = ArrayList<PostData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseFirestore.getInstance()

        binding.addButton.setOnClickListener {
                val intent = Intent(this, MakeEventActivity::class.java)
                startActivity(intent)
        }

        binding.calendarAddButton.setOnClickListener {
            val intent = Intent(this,CalendarActivity::class.java)
            startActivity(intent)
        }

        takeDatas()

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        recyclerViewAdapter = HomeRecyclerAdapter(postsList)
        binding.recyclerView.adapter = recyclerViewAdapter



    }

    fun takeDatas(){
        database.collection("Post").orderBy("date", Query.Direction.DESCENDING).addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                Toast.makeText(this,exception.localizedMessage,Toast.LENGTH_LONG).show()
            } else {

                if (snapshot != null ) {
                    if (!snapshot.isEmpty) {

                        val documents = snapshot.documents

                        postsList.clear()

                        for (document in documents) {
                           // val usernameMail = document.get("useremail") as String
                            val currentUsername = document.get("currentUsername") as String
                            val userComment = document.get("userComment") as String
                            val imageurl = document.get("imageurl") as String

                            val downloadPost = PostData(currentUsername,userComment,imageurl)
                            postsList.add(downloadPost)
                        }

                        recyclerViewAdapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }
}