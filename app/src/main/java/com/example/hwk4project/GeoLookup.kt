package com.example.hwk4project

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat

class GeoLookup : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var geoLatitudeEditText: EditText
    private lateinit var geoLongitudeEditText: EditText

    private lateinit var searchButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_geo_lookup)

        val sp = PreferenceManager.getDefaultSharedPreferences(this@GeoLookup)
        sp.registerOnSharedPreferenceChangeListener(this)

        val spReturnedDarkMode = sp.getBoolean("dark_mode_preference", false)
        var thisCL = findViewById<ConstraintLayout>(R.id.geoLookupCL)
        if (spReturnedDarkMode == false) {
            val drawable = ContextCompat.getDrawable(this, R.drawable.background1)
            thisCL.background = drawable
        } else {
            val drawable = ContextCompat.getDrawable(this, R.drawable.dark_mode_background)
            thisCL.background = drawable
        }

        geoLatitudeEditText = findViewById(R.id.geoLatitudeEditText)
        geoLongitudeEditText = findViewById(R.id.geoLongitudeEditText)
        searchButton = findViewById(R.id.searchButton)

        searchButton.setOnClickListener(){
            var latitude = geoLatitudeEditText.text.toString()
            var longitude = geoLongitudeEditText.text.toString()

            if(latitude.isBlank()){
                geoLatitudeEditText.error = "!"
                return@setOnClickListener
            }
            else if(longitude.isBlank()){
                geoLongitudeEditText.error ="!"
                return@setOnClickListener
            }
            else{
                var convertedLatitude = latitude.toDouble()
                var convertedLongitude = longitude.toDouble()

                val i = Intent(this@GeoLookup, Map::class.java)
                i.putExtra("transfered latitude",convertedLatitude)
                i.putExtra("transfered longitude",convertedLongitude)
                startActivity(i)
            }
        }//end of search button
    }// end of onCreate
    override fun onSharedPreferenceChanged(sp: SharedPreferences, key: String) {
        // Code that should run when a preference is updated
        // Get the shared preference key value and update the app with it
        Log.d("my tag", "onSharedPreferenceChanged called")


        //val sp: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        sp.registerOnSharedPreferenceChangeListener(this)
        val sp = PreferenceManager.getDefaultSharedPreferences(this@GeoLookup)

        var spReturnedDarkMode = sp.getBoolean("dark_mode_preference", false)
        var thisCL = findViewById<ConstraintLayout>(R.id.geoLookupCL)
        if (spReturnedDarkMode == false) {
            val drawable = ContextCompat.getDrawable(this, R.drawable.background1)
            thisCL.background = drawable
        } else {
            val drawable = ContextCompat.getDrawable(this, R.drawable.dark_mode_background)
            thisCL.background = drawable
        }
    }
}