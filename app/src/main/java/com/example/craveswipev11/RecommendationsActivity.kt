package com.example.craveswipev11

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomnavigation.BottomNavigationView

class RecommendationsActivity : AppCompatActivity() {

    private lateinit var savedPlacesRecyclerView: RecyclerView
    private lateinit var savedPlacesAdapter: SavedPlacesAdapter
    private lateinit var currentUser: User
    // private var savedPlaces = ArrayList<SavedPlace>()

    // Example saved places data (can later come from Firebase)
    private val savedPlaces = listOf(
        SavedPlace("La Piazza Italian", 4.5, "$$", "0.8 mi", LatLng(37.7749, -122.4194)),
        SavedPlace("Sushi Master", 4.8, "$$$", "1.2 mi", LatLng(37.7849, -122.4094)),
        SavedPlace("Taco Express", 4.2, "$", "0.5 mi", LatLng(37.7649, -122.4294))
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recommendations)

        // RecyclerView setup
        currentUser = MainActivity.currentUser
        for(value in currentUser.getRestaurant()){
            Log.d("MainActivity", value)
        }
        savedPlacesRecyclerView = findViewById(R.id.savedPlacesRecyclerView)
        savedPlacesRecyclerView.layoutManager = LinearLayoutManager(this)
        savedPlacesAdapter = SavedPlacesAdapter(savedPlaces)
        savedPlacesRecyclerView.adapter = savedPlacesAdapter


        // Google Map setup
//        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragmentContainer) as SupportMapFragment
//        mapFragment.getMapAsync { googleMap ->
//            addMarkers(googleMap)
//        }

        // Bottom navigation setup
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavBar)
        bottomNav.selectedItemId = R.id.nav_recommendations

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_choose -> {
                    val intent = Intent(this, SwipeActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_recommendations -> {
                    // Already here
                    true
                }
                else -> false
            }
        }
    }

//    private fun addMarkers(googleMap: GoogleMap) {
//        savedPlaces.forEach { place ->
//            googleMap.addMarker(
//                MarkerOptions().position(place.latLng).title(place.name)
//            )
//        }
//
//        // Move camera to first place
//        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(savedPlaces[0].latLng, 13f))
//    }

    data class SavedPlace(
        val name: String,
        val rating: Double,
        val price: String,
        val distance: String,
        val latLng: LatLng
    )
}
