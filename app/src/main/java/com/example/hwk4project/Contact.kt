package com.example.hwk4project

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.checkerframework.checker.units.qual.Length

class Contact : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var enterAddressContactEditText: EditText
    private lateinit var emailContactTextView: TextView
    private lateinit var firstNameContactTextView: TextView
    private lateinit var goButton: Button

    private lateinit var db : FirebaseFirestore
    private lateinit var mAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)

        db = FirebaseFirestore.getInstance()
        mAuth = FirebaseAuth.getInstance()

        enterAddressContactEditText = findViewById(R.id.enterAddressContactEditText)
        emailContactTextView = findViewById(R.id.emailContactTextView)
        firstNameContactTextView = findViewById(R.id.firstNameContactTextView)
        goButton = findViewById(R.id.goButton)

        val sp = PreferenceManager.getDefaultSharedPreferences(this@Contact)
        sp.registerOnSharedPreferenceChangeListener(this)

        val spReturnedDarkMode = sp.getBoolean("dark_mode_preference", false)
        var thisCL = findViewById<ConstraintLayout>(R.id.contactCL)
        if (spReturnedDarkMode == false) {
            val drawable = ContextCompat.getDrawable(this, R.drawable.background1)
            thisCL.background = drawable
        } else {
            val drawable = ContextCompat.getDrawable(this, R.drawable.dark_mode_background)
            thisCL.background = drawable
        }

        goButton.setOnClickListener(){
            var inputAddress = enterAddressContactEditText.text.toString()

            var userEmail = mAuth.currentUser?.email.toString()


            db.collection("hwk4Houses").whereEqualTo("address", inputAddress).get()
                .addOnSuccessListener { houses ->
                    if (houses.isEmpty) {
                        Toast.makeText(this, "No matches", Toast.LENGTH_SHORT).show()
                        return@addOnSuccessListener
                    }
                    var house = houses.first()
                    var userEmail = house.id
                    db.collection("hwk4Project").whereEqualTo("email", userEmail).get()
                        .addOnSuccessListener { document ->
                            if (document.isEmpty) {
                                Toast.makeText(this, "No matches", Toast.LENGTH_SHORT).show()
                                return@addOnSuccessListener
                            }

                            val document = document.first()
                            var firstName = document.getString("first")
                            var email = document.getString("email")

                            emailContactTextView.text = email.toString()
                            firstNameContactTextView.text = firstName.toString()
                        }
                        .addOnFailureListener { exception ->
                            Log.d("my tag", "Error getting documents.")
                        }
                }
                .addOnFailureListener { exception ->
                    Log.d("my tag", "Error getting document")
                }

        }




    }
    override fun onSharedPreferenceChanged(sp: SharedPreferences, key: String) {
        // Code that should run when a preference is updated
        // Get the shared preference key value and update the app with it
        Log.d("my tag","onSharedPreferenceChanged called")



        //val sp: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        sp.registerOnSharedPreferenceChangeListener(this)
        val sp = PreferenceManager.getDefaultSharedPreferences(this@Contact)

        var spReturnedDarkMode = sp.getBoolean("dark_mode_preference",false)
        var thisCL = findViewById<ConstraintLayout>(R.id.contactCL)
        if(spReturnedDarkMode == false){
            val drawable = ContextCompat.getDrawable(this, R.drawable.background1)
            thisCL.background = drawable
        }
        else{
            val drawable = ContextCompat.getDrawable(this, R.drawable.dark_mode_background)
            thisCL.background = drawable
        }

    }
}