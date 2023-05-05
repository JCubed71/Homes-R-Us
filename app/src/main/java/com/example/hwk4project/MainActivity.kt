package com.example.hwk4project


import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hwk4project.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.local.ReferenceSet

class MainActivity : AppCompatActivity(),
    SharedPreferences.OnSharedPreferenceChangeListener{

    private lateinit var recyclerViewHouses : RecyclerView
    private lateinit var data : ArrayList<House>
    private lateinit var houseAdapter : HouseAdapter

    private lateinit var binding : ActivityMainBinding
    private lateinit var bottomNavMenu : BottomNavigationView

    private lateinit var db : FirebaseFirestore
    private lateinit var mAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottomNavMenu = findViewById(R.id.bottomNavBarMA)

        val sp = PreferenceManager.getDefaultSharedPreferences(this@MainActivity)
        sp.registerOnSharedPreferenceChangeListener(this)

        val spReturnedDarkMode = sp.getBoolean("dark_mode_preference", false)
        var thisCL = findViewById<ConstraintLayout>(R.id.mainActivityCL)
        if (spReturnedDarkMode == false) {
            val drawable = ContextCompat.getDrawable(this, R.drawable.background1)
            thisCL.background = drawable
        } else {
            val drawable = ContextCompat.getDrawable(this, R.drawable.dark_mode_background)
            thisCL.background = drawable
        }


        db = FirebaseFirestore.getInstance()
        mAuth = FirebaseAuth.getInstance()

        recyclerViewHouses = findViewById<RecyclerView>(R.id.recyclerViewHouses)
        recyclerViewHouses.layoutManager = LinearLayoutManager(this)
        data = ArrayList<House>()
        db.collection("hwk4Houses").get().addOnSuccessListener { result ->
            for (document in result) {
                Log.d("my tag", "${document.id} => ${document.data}")
                var documentAddress : String = document.data.get("address").toString()
                var documentBedroom : Long = document.data.get("bedrooms") as Long
                var documentBathroom : Long = document.data.get("bathrooms")as Long
                var documentLatitude : Double = document.data.get("latitude") as Double
                var documentLongitude : Double = document.data.get("longitude")as Double
                var documentPrice : Long = document.data.get("price") as Long
                var convertedDocumentBedroom = documentBedroom.toFloat()
                var convertedDocumentBathroom = documentBathroom.toFloat()
                var convertedDocumentLatitude = documentLatitude.toDouble()
                var convertedDocumentLongitude = documentLongitude.toDouble()
                var convertedDocumentPrice = documentPrice.toFloat()
                data.add(House(documentAddress, convertedDocumentBedroom, convertedDocumentBathroom, convertedDocumentLatitude, convertedDocumentLongitude, convertedDocumentPrice))

            }
            houseAdapter = HouseAdapter(data)
            recyclerViewHouses.adapter = houseAdapter
        }
            .addOnFailureListener { exception ->
                Log.w("my tag", "Error getting documents.", exception)
            }
        /*houseAdapter = HouseAdapter(data)
        recyclerViewHouses.adapter = houseAdapter

        houseAdapter.setOnItemClickListener(object : HouseAdapter.HouseAdapterListener{
            override fun onClick(position: Int){
                //var name = data[position].address.toString()
                //Log.d("test",name)
            }
        })*/

        bottomNavMenu.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.filterItem -> {
                    val goToFilterActivity = Intent(this@MainActivity, Filter::class.java)
                    startActivity(goToFilterActivity)
                    true
                }
                R.id.addHouseItem -> {
                    val goToAddHouseActivity = Intent(this@MainActivity, AddHouse::class.java)
                    startActivity(goToAddHouseActivity)
                    true
                }
                R.id.geoLookupItem -> {
                    val goToGeoLookupActivity = Intent(this@MainActivity, GeoLookup::class.java)
                    startActivity(goToGeoLookupActivity)
                    true
                }
                R.id.contactItem -> {
                    val goToContactActivity = Intent(this@MainActivity, Contact::class.java)
                    startActivity(goToContactActivity)
                    true
                }
                else -> {
                    true
                }

            }
        }




    }//end of oncreate
    override fun onRestart(){
        super.onRestart()

        Log.d("my tag","MA onRestartCalled")
        val sp = PreferenceManager.getDefaultSharedPreferences(this@MainActivity)
        var returnedMaxPrice = sp.getFloat("maxPrice", 5000000f)
        var returnedMinimumBedrooms = sp.getFloat("minimumBedrooms",0f)
        var returnedminimumBathrooms = sp.getFloat("minimumBathrooms",0f)


        db = FirebaseFirestore.getInstance()
        recyclerViewHouses = findViewById<RecyclerView>(R.id.recyclerViewHouses)
        recyclerViewHouses.layoutManager = LinearLayoutManager(this)
        data = ArrayList<House>()
        db.collection("hwk4Houses").get().addOnSuccessListener { result ->
            for (document in result) {
                Log.d("MYDEBUG", "${document.id} => ${document.data}")
                var documentPrice : Long = document.data.get("price") as Long
                var documentBedrooms : Long = document.data.get("bedrooms") as Long
                var documentBathrooms : Long = document.data.get("bathrooms") as Long
                if((documentPrice <= returnedMaxPrice) && (documentBedrooms >= returnedMinimumBedrooms) && (documentBathrooms >= returnedminimumBathrooms)){
                    var documentAddress : String = document.data.get("address").toString()
                    var documentBedroom : Long = document.data.get("bedrooms") as Long
                    var documentBathroom : Long = document.data.get("bathrooms")as Long
                    var documentLatitude : Double = document.data.get("latitude") as Double
                    var documentLongitude : Double = document.data.get("longitude")as Double
                    var documentPrice : Long = document.data.get("price") as Long
                    var convertedDocumentBedroom = documentBedroom.toFloat()
                    var convertedDocumentBathroom = documentBathroom.toFloat()
                    var convertedDocumentLatitude = documentLatitude.toDouble()
                    var convertedDocumentLongitude = documentLongitude.toDouble()
                    var convertedDocumentPrice = documentPrice.toFloat()
                    data.add(House(documentAddress, convertedDocumentBedroom, convertedDocumentBathroom, convertedDocumentLatitude, convertedDocumentLongitude, convertedDocumentPrice))


                }//end of if
            }//end of for loop
            houseAdapter = HouseAdapter(data)
            recyclerViewHouses.adapter = houseAdapter
        }
            .addOnFailureListener { exception ->
                Log.w("my tag", "Error getting documents.", exception)
            }

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.getItemId()) {
            R.id.settingsMenuItem -> {
                val goToSettingsActivity = Intent(this@MainActivity, Settings::class.java)
                startActivity(goToSettingsActivity)
                //code needed here
                true
            }
            R.id.profileMenuItem -> {
                val goToProfileActivity = Intent(this@MainActivity, Profile::class.java)
                startActivity(goToProfileActivity)
                //code needed here
                true
            }
            R.id.signOutMenuItem -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    override fun onSharedPreferenceChanged(sp: SharedPreferences, key: String) {
        // Code that should run when a preference is updated
        // Get the shared preference key value and update the app with it
        Log.d("my tag","onSharedPreferenceChanged called")



        //val sp: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        sp.registerOnSharedPreferenceChangeListener(this)
        val sp = PreferenceManager.getDefaultSharedPreferences(this@MainActivity)

        var spReturnedDarkMode = sp.getBoolean("dark_mode_preference",false)
        var thisCL = findViewById<ConstraintLayout>(R.id.mainActivityCL)
        if(spReturnedDarkMode == false){
            val drawable = ContextCompat.getDrawable(this, R.drawable.background1)
            thisCL.background = drawable
        }
        else{
            val drawable = ContextCompat.getDrawable(this, R.drawable.dark_mode_background)
            thisCL.background = drawable
        }





    }

    override fun onResume() {
        super.onResume()



    }

}