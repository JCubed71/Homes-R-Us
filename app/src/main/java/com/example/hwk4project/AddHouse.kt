package com.example.hwk4project

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AddHouse : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var addressEditText: EditText
    private lateinit var bedroomsEditText : EditText
    private lateinit var bathroomsEditText: EditText
    private lateinit var longitudeEditText: EditText
    private lateinit var latitudeEditText: EditText
    private lateinit var priceEditText: EditText

    private lateinit var addHouseButton: Button

    private lateinit var db : FirebaseFirestore
    private lateinit var mAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_house)

        val sp = PreferenceManager.getDefaultSharedPreferences(this@AddHouse)
        sp.registerOnSharedPreferenceChangeListener(this)

        val spReturnedDarkMode = sp.getBoolean("dark_mode_preference", false)
        var thisCL = findViewById<ConstraintLayout>(R.id.addHouseCL)
        if (spReturnedDarkMode == false) {
            val drawable = ContextCompat.getDrawable(this, R.drawable.background1)
            thisCL.background = drawable
        } else {
            val drawable = ContextCompat.getDrawable(this, R.drawable.dark_mode_background)
            thisCL.background = drawable
        }


        db = FirebaseFirestore.getInstance()
        mAuth = FirebaseAuth.getInstance()

        addressEditText = findViewById(R.id.addressEditText)
        bedroomsEditText = findViewById(R.id.bedroomsEditText)
        bathroomsEditText = findViewById(R.id.bathroomsEditText)
        latitudeEditText = findViewById(R.id.lattudeEditText)
        longitudeEditText = findViewById(R.id.longitudeEditText)
        priceEditText = findViewById(R.id.priceEditText)
        addHouseButton = findViewById(R.id.addHouseButton)



        addHouseButton.setOnClickListener(){
            var address = addressEditText.text.toString()
            var bedrooms = bedroomsEditText.text.toString()
            var bathrooms = bathroomsEditText.text.toString()
            var latitude = latitudeEditText.text.toString()
            var longitude = longitudeEditText.text.toString()
            var price = priceEditText.text.toString()

            if(address.isBlank()){
                addressEditText.error = "!"
                return@setOnClickListener
            }
            else if(bedrooms.isBlank()){
                bedroomsEditText.error ="!"
                return@setOnClickListener
            }
            else if(bathrooms.isBlank()){
                bathroomsEditText.error = "!"
                return@setOnClickListener
            }
            else if(latitude.isBlank()){
                latitudeEditText.error = "!"
                return@setOnClickListener
            }
            else if(longitude.isBlank()){
                longitudeEditText.error = "!"
                return@setOnClickListener
            }
            else if(price.isBlank()){
                priceEditText.error = "!"
                return@setOnClickListener
            }
            else{
                var convertedBedrooms = bedrooms.toLong()
                var convertedBathrooms = bathrooms.toLong()
                var convertedLatitude = latitude.toDouble()
                var convertedLongitude = longitude.toDouble()
                var convertedPrice = price.toInt()

                var email = mAuth.currentUser?.email.toString()

                val data = hashMapOf(
                    "email" to email,
                    "address" to address,
                    "bedrooms" to convertedBedrooms,
                    "bathrooms" to convertedBathrooms,
                    "latitude" to convertedLatitude,
                    "longitude" to convertedLongitude,
                    "price" to convertedPrice
                )
                val documentRef = db.collection("hwk4Houses").document(email)
                documentRef.set(data).addOnSuccessListener {
                    Toast.makeText(baseContext, "House added", Toast.LENGTH_SHORT).show()
                    Log.d("my tag", "House added")
                    finish()

                }
                    .addOnFailureListener{e ->
                        Toast.makeText(baseContext, "Error adding house", Toast.LENGTH_SHORT).show()
                        Log.w("my tag", "Error adding house", e)
                    }
            }
        }


    }
    override fun onSharedPreferenceChanged(sp: SharedPreferences, key: String) {
        // Code that should run when a preference is updated
        // Get the shared preference key value and update the app with it
        Log.d("my tag", "onSharedPreferenceChanged called")


        //val sp: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        sp.registerOnSharedPreferenceChangeListener(this)
        val sp = PreferenceManager.getDefaultSharedPreferences(this@AddHouse)

        var spReturnedDarkMode = sp.getBoolean("dark_mode_preference", false)
        var thisCL = findViewById<ConstraintLayout>(R.id.addHouseCL)
        if (spReturnedDarkMode == false) {
            val drawable = ContextCompat.getDrawable(this, R.drawable.background1)
            thisCL.background = drawable
        } else {
            val drawable = ContextCompat.getDrawable(this, R.drawable.dark_mode_background)
            thisCL.background = drawable
        }
    }
}