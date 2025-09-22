package com.example.craveswipev11

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.craveswipev11.cuisineRelated.Cuisine
import com.example.craveswipev11.cuisineRelated.CuisineAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    companion object{
        lateinit var currentUser : User
    }

    private lateinit var budgetProgressBar: ProgressBar
    private lateinit var priceTextView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cuisines = listOf(
            Cuisine(R.drawable.chinese, "Chinese"),
            Cuisine(R.drawable.african, "African"),
            Cuisine(R.drawable.american, "American"),
            Cuisine(R.drawable.caribbean, "Caribbean"),
            Cuisine(R.drawable.japanese, "Japanese"),
            Cuisine(R.drawable.indian, "Indian"),
            Cuisine(R.drawable.italian, "Italian"),
            Cuisine(R.drawable.mediterranean, "Mediterranean"),
            Cuisine(R.drawable.latin, "Latin"),
            Cuisine(R.drawable.thai, "Thai"),
            Cuisine(R.drawable.mexican, "Mexican"),
            Cuisine(R.drawable.vietnamese, "Vietnamese"),
            Cuisine(R.drawable.korean, "Korean"),
            Cuisine(R.drawable.buffet, "Buffet"),
            Cuisine(R.drawable.cafeteria, "Cafeteria"),
            Cuisine(R.drawable.fast, "Fast Food")
        )

        val minRating = findViewById<EditText>(R.id.minRatingInput).text
        val maxRating = findViewById<EditText>(R.id.maxRatingInput).text
        val budgetVal = findViewById<SeekBar>(R.id.priceSeekBar)
        val startButt = findViewById<Button>(R.id.startExploringButton)
        budgetProgressBar = findViewById(R.id.budgetProgressBar)
        priceTextView = findViewById(R.id.priceText)
        var userCuisine : String = ""
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavBar)


        val recyclerView: RecyclerView = findViewById(R.id.cuisineRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = CuisineAdapter(cuisines) { position ->
            val selected = cuisines[position]
            if(userCuisine.compareTo("") == 0) {
                Toast.makeText(this, "Selected ${selected.name} Cuisine", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "Switched to ${selected.name} Cuisine", Toast.LENGTH_SHORT).show()
            }
            userCuisine = selected.name
        }

        startButt.setOnClickListener{
            val budgetValue = budgetVal.progress.toString().toInt()
            val minValue = minRating.toString()
            val maxValue = maxRating.toString()
            if(userCuisine.compareTo("") == 0){
                Toast.makeText(this, "Select a Cuisine", Toast.LENGTH_SHORT).show()
            }else if(budgetValue == 0){
                Toast.makeText(this, "Enter your Budget Value", Toast.LENGTH_SHORT).show()
            }else if(minValue.compareTo("") == 0){
                Toast.makeText(this, "Enter Minimum Rating!", Toast.LENGTH_SHORT).show()
            }else if(maxValue.compareTo("") == 0){
                Toast.makeText(this, "Enter Maximum Rating!", Toast.LENGTH_SHORT).show()
            }else{
                currentUser = User(userCuisine,budgetValue,minValue.toDouble(),maxValue.toDouble())
                val intent = Intent(this, SwipeActivity::class.java)
                startActivity(intent)
            }
        }

        budgetVal.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // Update ProgressBar and TextView
                budgetProgressBar.progress = progress
                priceTextView.text = "$progress"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Optional
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Optional
            }
        })


        // navigation related
        bottomNav.selectedItemId = R.id.nav_home

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    // Already in Home
                    true
                }
                R.id.nav_choose -> {
                    val intent = Intent(this, SwipeActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_recommendations -> {
                    val intent = Intent(this, RecommendationsActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }
}
