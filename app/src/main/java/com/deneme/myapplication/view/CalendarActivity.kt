package com.deneme.myapplication.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.deneme.myapplication.R
import com.deneme.myapplication.databinding.ActivityCalendarBinding
import com.google.firebase.firestore.FirebaseFirestore

class CalendarActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCalendarBinding
    private lateinit var database : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        binding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseFirestore.getInstance()

        binding.btnEtkinlikOlustur.setOnClickListener {
            val tarih = binding.etTarih.text.toString()

            if (tarih.isNotEmpty()) {
                val event = hashMapOf(
                    "tarih" to tarih
                )

                database.collection("Event").add(event)
                    .addOnSuccessListener {
                        finish()
                    }
                    .addOnFailureListener {exception ->
                        Toast.makeText(applicationContext,exception.localizedMessage, Toast.LENGTH_LONG).show()
                    }
            } else {
                binding.etTarih.error = "Etkinlik tarihi boş bırakılamaz"
            }
        }

    }
}