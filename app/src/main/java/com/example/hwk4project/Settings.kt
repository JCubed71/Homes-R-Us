package com.example.hwk4project

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

class Settings : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)


        val sp = PreferenceManager.getDefaultSharedPreferences(this@Settings)
        sp.registerOnSharedPreferenceChangeListener(this)

        val spReturnedDarkMode = sp.getBoolean("dark_mode_preference", false)
        var thisCL = findViewById<ConstraintLayout>(R.id.settingsFragmentConstraintlayout)
        if (spReturnedDarkMode == false) {
            val drawable = ContextCompat.getDrawable(this, R.drawable.background1)
            thisCL.background = drawable
        } else {
            val drawable = ContextCompat.getDrawable(this, R.drawable.dark_mode_background)
            thisCL.background = drawable
        }

        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        val f: Fragment = SettingsFragment()
        ft.add(R.id.settingsFragmentConstraintlayout, f)
        ft.commit()
    }
    override fun onSharedPreferenceChanged(sp: SharedPreferences, key: String) {
        // Code that should run when a preference is updated
        // Get the shared preference key value and update the app with it
        Log.d("my tag", "onSharedPreferenceChanged called")


        //val sp: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        sp.registerOnSharedPreferenceChangeListener(this)
        val sp = PreferenceManager.getDefaultSharedPreferences(this@Settings)

        var spReturnedDarkMode = sp.getBoolean("dark_mode_preference", false)
        var thisCL = findViewById<ConstraintLayout>(R.id.settingsFragmentConstraintlayout)
        if (spReturnedDarkMode == false) {
            val drawable = ContextCompat.getDrawable(this, R.drawable.background1)
            thisCL.background = drawable
        } else {
            val drawable = ContextCompat.getDrawable(this, R.drawable.dark_mode_background)
            thisCL.background = drawable
        }
    }
}