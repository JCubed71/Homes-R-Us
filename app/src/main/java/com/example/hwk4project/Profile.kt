package com.example.hwk4project

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore

class Profile : AppCompatActivity(),SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var emailProfileTextView: TextView
    private lateinit var firstNameProfileTextView: TextView
    private lateinit var  lastNameProfileTextView: TextView
    private lateinit var phoneNumberProfileTextView: TextView
    private lateinit var numberOfHomesProfileTextView: TextView

    private lateinit var db : FirebaseFirestore
    private lateinit var mAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        db = FirebaseFirestore.getInstance()
        mAuth = FirebaseAuth.getInstance()

        emailProfileTextView = findViewById(R.id.emailProfileTextView)
        firstNameProfileTextView = findViewById(R.id.firstNameProfileTextView)
        lastNameProfileTextView = findViewById(R.id.lastNameProfileTextView)
        phoneNumberProfileTextView = findViewById(R.id.phoneNumberProfileTextView)
        numberOfHomesProfileTextView = findViewById(R.id.numberOfListedHomesProfileTextView)

        var firstName : String = "null"
        var lastName : String = "null"
        var phone : String = "null"
        var count : Int = 0


        val sp = PreferenceManager.getDefaultSharedPreferences(this@Profile)
        sp.registerOnSharedPreferenceChangeListener(this)

        val spReturnedDarkMode = sp.getBoolean("dark_mode_preference", false)
        var thisCL = findViewById<ConstraintLayout>(R.id.profileCL)
        if (spReturnedDarkMode == false) {
            val drawable = ContextCompat.getDrawable(this, R.drawable.background1)
            thisCL.background = drawable
        } else {
            val drawable = ContextCompat.getDrawable(this, R.drawable.dark_mode_background)
            thisCL.background = drawable
        }

        var userEmail = mAuth.currentUser?.email.toString()
        //userEmail = userEmail.lowercase()
        db.collection("hwk4Project").get().addOnSuccessListener { result ->
            for (document in result) {
                val email = document.getString("email")
                if (email != null && email.equals(userEmail, ignoreCase = true)) {
                    firstName = document.getString("first").toString()
                    lastName = document.getString("last").toString()
                    phone = document.getString("phone").toString()
                    break
                }
            }
            emailProfileTextView.text = userEmail
            firstNameProfileTextView.text = firstName
            lastNameProfileTextView.text = lastName
            phoneNumberProfileTextView.text = phone
        }.addOnFailureListener { exception ->
            Log.d("my tag", "Error getting documents.")
        }

        db.collection("hwk4Houses").whereEqualTo(FieldPath.documentId(), userEmail).get()
            .addOnSuccessListener { documents ->
                count = documents.size()
                numberOfHomesProfileTextView.text = count.toString()
            }
            .addOnFailureListener { exception ->
                Log.d("my tag", "Error getting documents.")
            }


    }
    override fun onSharedPreferenceChanged(sp: SharedPreferences, key: String) {
        // Code that should run when a preference is updated
        // Get the shared preference key value and update the app with it
        Log.d("my tag", "onSharedPreferenceChanged called")


        //val sp: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        sp.registerOnSharedPreferenceChangeListener(this)
        val sp = PreferenceManager.getDefaultSharedPreferences(this@Profile)

        var spReturnedDarkMode = sp.getBoolean("dark_mode_preference", false)
        var thisCL = findViewById<ConstraintLayout>(R.id.profileCL)
        if (spReturnedDarkMode == false) {
            val drawable = ContextCompat.getDrawable(this, R.drawable.background1)
            thisCL.background = drawable
        } else {
            val drawable = ContextCompat.getDrawable(this, R.drawable.dark_mode_background)
            thisCL.background = drawable
        }
    }
}