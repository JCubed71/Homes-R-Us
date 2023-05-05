package com.example.hwk4project

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import com.example.hwk4project.databinding.ActivityMainBinding
import com.example.hwk4project.databinding.ActivityMapBinding
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class Map : AppCompatActivity(), OnMapReadyCallback,
SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var binding: ActivityMapBinding
    private lateinit var myMap : MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("my tag","Map first onCreate")
        super.onCreate(savedInstanceState)
        MapsInitializer.initialize(this)
        setContentView(R.layout.activity_map)

        //binding = ActivityMapBinding.inflate((layoutInflater))
        //setContentView(binding.root)
        Log.d("my tag","Map second onCreate")

        val sp: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        sp.registerOnSharedPreferenceChangeListener(this)

        var spReturnedMapZoom = sp.getString("map_zoom_preference","15f")
        var convertedReturnedMapZoom : Float = spReturnedMapZoom!!.toFloat()

        var i = intent
        var returnedLatitude = i.getDoubleExtra("transfered latitude", 0.00)
        var returnedLongitude = i.getDoubleExtra("transfered longitude", 0.00)

        myMap = findViewById(R.id.map)
        myMap.onCreate(savedInstanceState)
        myMap.getMapAsync(this)

        val location = LatLng(returnedLatitude, returnedLongitude)

        val markerOptions = MarkerOptions().position(location)

        val cameraPosition = CameraPosition.Builder().target(location).zoom(convertedReturnedMapZoom).build()

        myMap.getMapAsync { googleMap ->
            googleMap.addMarker(markerOptions)
            googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        }

        /*mapFragment.getMapAsync(this)
        val here = LatLng(returnedLatitude,returnedLongitude)
        mapFragment.getMapAsync { googleMap ->
            googleMap.addMarker(MarkerOptions().position(here))
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(here))
            googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(here, 10f)) //Need map zoom sp
        }*/

    }

    override fun onMapReady(googleMap: GoogleMap) {
        Log.d("my tag","onMapReady Called")
        /*var i = intent
        var returnedLatitude = i.getDoubleExtra("transfered latitude", 0.00)
        var returnedLongitude = i.getDoubleExtra("transfered longitude", 0.00)

        myMap = findViewById(R.id.map)
        myMap.getMapAsync(this)

        val location = LatLng(returnedLatitude, returnedLongitude)

        val markerOptions = MarkerOptions().position(location)

        val cameraPosition = CameraPosition.Builder().target(location).zoom(12.0f).build()

        myMap.getMapAsync { googleMap ->
            googleMap.addMarker(markerOptions)
            googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        }
        myMap.onResume()*/
    }

    override fun onResume() {
        super.onResume()
        myMap.onResume()
    }

    override fun onPause() {
        super.onPause()
        myMap.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        myMap.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        myMap.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        myMap.onLowMemory()
    }
    override fun onSharedPreferenceChanged(sp: SharedPreferences, key: String) {
        // Code that should run when a preference is updated
        // Get the shared preference key value and update the app with it
        Log.d("my tag","onSharedPreferenceChanged called in Map Activity")

        //val sp: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        sp.registerOnSharedPreferenceChangeListener(this)
        val sp = PreferenceManager.getDefaultSharedPreferences(this@Map)


        var spReturnedMapZoom = sp.getString("map_zoom_preference","15f")
        var convertedReturnedMapZoom : Float = spReturnedMapZoom!!.toFloat()



    }
}