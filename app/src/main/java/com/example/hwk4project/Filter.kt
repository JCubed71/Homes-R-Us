package com.example.hwk4project

import android.content.Context
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

class Filter : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var setFilterButton : Button
    private lateinit var clearFilterButton: Button

    private lateinit var maxPriceEditText: EditText
    private lateinit var minimumBedroomsEditText: EditText
    private lateinit var minimumBathroomsEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)

        val sp = PreferenceManager.getDefaultSharedPreferences(this@Filter)
        sp.registerOnSharedPreferenceChangeListener(this)

        val spReturnedDarkMode = sp.getBoolean("dark_mode_preference", false)
        var thisCL = findViewById<ConstraintLayout>(R.id.filterCL)
        if (spReturnedDarkMode == false) {
            val drawable = ContextCompat.getDrawable(this, R.drawable.background1)
            thisCL.background = drawable
        } else {
            val drawable = ContextCompat.getDrawable(this, R.drawable.dark_mode_background)
            thisCL.background = drawable
        }

        setFilterButton = findViewById(R.id.setFilterButton)
        clearFilterButton = findViewById(R.id.clearFilterButton)
        maxPriceEditText = findViewById(R.id.maxPriceEditText)
        minimumBedroomsEditText = findViewById(R.id.minimumBedroomsEditText)
        minimumBathroomsEditText = findViewById(R.id.minimumBathroomsEditText)

        var maxPrice : Float
        var minimumBedrooms : Float
        var minimumBathrooms : Float

        setFilterButton.setOnClickListener(){
            if(maxPriceEditText.toString().isEmpty()){
                maxPrice = maxPriceEditText.text.toString().toFloat()
            }else{
                maxPrice = 5000000f
            }
            if(minimumBedroomsEditText.text.isEmpty()){
                minimumBedrooms = 0f
            }else{
                minimumBedrooms = minimumBedroomsEditText.text.toString().toFloat()
            }
            if(minimumBathroomsEditText.text.isEmpty()){
                minimumBathrooms = 0f
            }else{
                minimumBathrooms = minimumBathroomsEditText.text.toString().toFloat()
            }


            val context: Context = this@Filter
            val sp = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = sp.edit()
            editor.putFloat("maxPrice", maxPrice)
            editor.putFloat("minimumBedrooms", minimumBedrooms)
            editor.putFloat("minimumBathrooms", minimumBathrooms)
            editor.apply()


            Toast.makeText(baseContext, "Filter Set", Toast.LENGTH_SHORT).show()
            finish()
        }
        clearFilterButton.setOnClickListener(){
            val context: Context = this@Filter
            val sp = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = sp.edit()

            editor.putFloat("maxPrice",5000000f).clear()
            editor.putFloat("minimumBedrooms",0f).clear()
            editor.putFloat("minimumBathrooms",0f).clear()
            editor.apply()

            Toast.makeText(baseContext, "Filter cleared", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
    override fun onSharedPreferenceChanged(sp: SharedPreferences, key: String) {
        // Code that should run when a preference is updated
        // Get the shared preference key value and update the app with it
        Log.d("my tag", "onSharedPreferenceChanged called")


        //val sp: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        sp.registerOnSharedPreferenceChangeListener(this)
        val sp = PreferenceManager.getDefaultSharedPreferences(this@Filter)

        var spReturnedDarkMode = sp.getBoolean("dark_mode_preference", false)
        var thisCL = findViewById<ConstraintLayout>(R.id.filterCL)
        if (spReturnedDarkMode == false) {
            val drawable = ContextCompat.getDrawable(this, R.drawable.background1)
            thisCL.background = drawable
        } else {
            val drawable = ContextCompat.getDrawable(this, R.drawable.dark_mode_background)
            thisCL.background = drawable
        }
    }
}