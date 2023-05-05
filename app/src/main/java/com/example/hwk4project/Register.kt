package com.example.hwk4project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import android.util.Patterns
import android.widget.Toast


class Register : AppCompatActivity() {

    private lateinit var emailEditText : EditText
    private lateinit var passwordEditText : EditText
    private lateinit var phoneEditText : EditText
    private lateinit var firstNameEditText : EditText
    private lateinit var lastNameEditText : EditText

    private lateinit var registerButton : Button

    private lateinit var db : FirebaseFirestore
    private lateinit var mAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        db = FirebaseFirestore.getInstance()
        mAuth = FirebaseAuth.getInstance()

        emailEditText = findViewById(R.id.emailEditTextRegister)
        passwordEditText = findViewById(R.id.passwordEditTextRegister)
        phoneEditText = findViewById(R.id.phoneEditText)
        firstNameEditText = findViewById(R.id.firstNameEditText)
        lastNameEditText = findViewById(R.id.lastNameEditText)
        registerButton = findViewById(R.id.registerButton)

        registerButton.setOnClickListener(){
            var email = emailEditText.text.toString()
            var password = passwordEditText.text.toString()
            var phone = phoneEditText.text.toString()
            var firstName = firstNameEditText.text.toString()
            var lastName = lastNameEditText.text.toString()

            if(email.isBlank()){
                emailEditText.error = "!"
                return@setOnClickListener
            }
            else if(password.isBlank()){
                passwordEditText.error ="!"
                return@setOnClickListener
            }
            else if(phone.isBlank()){
                phoneEditText.error = "!"
                return@setOnClickListener
            }
            else if(firstName.isBlank()){
                firstNameEditText.error = "!"
                return@setOnClickListener
            }
            else if(lastName.isBlank()){
                lastNameEditText.error = "!"
                return@setOnClickListener
            }
            else{ //No errors -> create account
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) {
                    if (it.isSuccessful) {
                        Toast.makeText(this, "Successful", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show()
                    }
                }
                phone.toLong()
                val data = hashMapOf(
                    "email" to email,
                    "first" to firstName,
                    "last" to lastName,
                    "phone" to phone
                )
                //end of db.collection
                /*val documentRef = db.collection("hwk4Project").document(email)
                documentRef.set(datas).addOnSuccessListener {
                    Log.d("MYDEBUGGER", "DocumentSnapShot added")

                }.addOnFailureListener{e ->
                        Log.w("MYDEBUGGER", "Error adding info", e)
                }*/

                Thread.sleep(3000)
                mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val user = mAuth.currentUser
                            val documentRef = db.collection("hwk4Project").document(email)
                            documentRef.set(data).addOnSuccessListener {
                                Log.d("my tag", "Document added")

                            }
                                .addOnFailureListener{e ->
                                    Log.w("my tag", "Error adding user", e)
                                }
                            val goToMainActivity = Intent(this@Register, MainActivity::class.java)
                            startActivity(goToMainActivity)


                        } else {

                            Log.w("my tag", "sign in failed", task.exception)
                            Toast.makeText(baseContext, "Login Failed", Toast.LENGTH_SHORT).show()
                        }
                    }
            }// end of else (create account)

        }//end of OnClickListener

    }
}