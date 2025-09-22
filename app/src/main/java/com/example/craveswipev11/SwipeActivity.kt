package com.example.craveswipev11

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.android.material.bottomnavigation.BottomNavigationView

class SwipeActivity : AppCompatActivity() {

    private lateinit var cardView: CardView
    private lateinit var restaurantImage: ImageView
    private lateinit var restaurantName: TextView
    private lateinit var restaurantRating: TextView
    private lateinit var restaurantInfo: TextView

    private var currentIndex = 0

    private lateinit var currentUser : User

    // Fake Restaurant Data (placeholder)
    private val restaurantList = listOf(
        Restaurant("La Piazza Italian", "★★★★☆ (128)", "Italian · $$ · 0.8 mi", R.drawable.placeholder_image),
        Restaurant("Sushi World", "★★★★★ (220)", "Japanese · $$$ · 1.2 mi", R.drawable.placeholder_image),
        Restaurant("Burger Hub", "★★★☆☆ (98)", "American · $ · 0.5 mi", R.drawable.placeholder_image),
        Restaurant("Taco Fiesta", "★★★★☆ (150)", "Mexican · $$ · 2.0 mi", R.drawable.placeholder_image)
    )

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swipe)

        // Bind views
        cardView = findViewById(R.id.restaurantCard)
        restaurantImage = findViewById(R.id.restaurantImage)
        restaurantName = findViewById(R.id.restaurantName)
        restaurantRating = findViewById(R.id.restaurantRating)
        restaurantInfo = findViewById(R.id.restaurantInfo)
        currentUser = MainActivity.currentUser

        // Load first restaurant
        loadRestaurant()

        // Swipe detection on card
        cardView.setOnTouchListener(object : View.OnTouchListener {
            private var downX = 0f
            private val SWIPE_THRESHOLD = 200

            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        downX = event.x
                        return true
                    }

                    MotionEvent.ACTION_MOVE -> {
                        val deltaX = event.x - downX

                        cardView.translationX = deltaX
                        cardView.rotation = deltaX / 20 // tilt effect

                        return true
                    }

                    MotionEvent.ACTION_UP -> {
                        val deltaX = event.x - downX

                        if (deltaX > SWIPE_THRESHOLD) {
                            // Animate out to right
                            cardView.animate()
                                .translationX(1000f)
                                .rotation(30f)
                                .setDuration(300)
                                .withEndAction {
                                    resetCardPosition()
                                    Toast.makeText(this@SwipeActivity, "Liked!", Toast.LENGTH_SHORT).show()
                                    currentUser.addRestaurant(restaurantList[currentIndex].name)
                                    nextRestaurant()
                                }
                        } else if (deltaX < -SWIPE_THRESHOLD) {
                            // Animate out to left
                            cardView.animate()
                                .translationX(-1000f)
                                .rotation(-30f)
                                .setDuration(300)
                                .withEndAction {
                                    resetCardPosition()
                                    Toast.makeText(this@SwipeActivity, "Skipped!", Toast.LENGTH_SHORT).show()
                                    nextRestaurant()
                                }
                        } else {
                            // Not enough swipe → animate back to center
                            cardView.animate()
                                .translationX(0f)
                                .rotation(0f)
                                .setDuration(200)
                        }

                        return true
                    }
                }
                return false
            }

            private fun resetCardPosition() {
                cardView.translationX = 0f
                cardView.rotation = 0f
            }
        })


        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavBar)

        bottomNav.selectedItemId = R.id.nav_choose

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_choose -> {

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

    // Load restaurant from list
    private fun loadRestaurant() {
        if (currentIndex >= restaurantList.size) {
            // Restart or show finished
            Toast.makeText(this, "No more restaurants!", Toast.LENGTH_SHORT).show()
            return
        }

        val restaurant = restaurantList[currentIndex]

        restaurantName.text = restaurant.name
        restaurantRating.text = restaurant.rating
        restaurantInfo.text = restaurant.info
        restaurantImage.setImageResource(restaurant.imageResId)
    }

    // Go to next restaurant
    private fun nextRestaurant() {
        currentIndex++
        loadRestaurant()
    }




    // Data class for restaurants
    data class Restaurant(
        val name: String,
        val rating: String,
        val info: String,
        val imageResId: Int
    )
}
