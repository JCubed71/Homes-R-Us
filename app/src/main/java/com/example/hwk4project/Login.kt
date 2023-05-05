package com.example.hwk4project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Login : AppCompatActivity() {

    private lateinit var db : FirebaseFirestore
    private lateinit var mAuth : FirebaseAuth

    private lateinit var myLoginButton : Button
    private lateinit var myRegisterTextView : TextView

    private lateinit var emailEditText : EditText
    private lateinit var passwordEditText : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        myLoginButton = findViewById<Button>(R.id.myLoginButton)
        myRegisterTextView = findViewById(R.id.myRegisterTextView)

        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)

        db = FirebaseFirestore.getInstance()
        mAuth = FirebaseAuth.getInstance()

        myLoginButton.setOnClickListener(){
            var email = emailEditText.text.toString()
            var password = passwordEditText.text.toString()
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = mAuth.currentUser
                        val goToMainActivity = Intent(this@Login, MainActivity::class.java)
                        startActivity(goToMainActivity)


                    } else {
                        Log.d("my tag", "signInWithEmail failed", task.exception)
                        Toast.makeText(baseContext, "Login Failed", Toast.LENGTH_SHORT).show()
                    }
                }

        } //end of in button on click listener

        myRegisterTextView.setOnClickListener(){
            val goToRegisterActivity = Intent(this@Login, Register::class.java)
            startActivity(goToRegisterActivity)
        }


    }
}