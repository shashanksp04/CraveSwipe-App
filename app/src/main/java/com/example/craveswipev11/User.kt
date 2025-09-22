package com.example.craveswipev11

import com.example.craveswipev11.cuisineRelated.Cuisine

class User(private var cuisine: String, private var budget : Int,
           private var maxRating : Double, private var minRating : Double ) {
    private var preferredRestaurants : ArrayList<String> = ArrayList<String>()

    public fun addRestaurant(name : String){
        this.preferredRestaurants.add(name);
    }

    public fun getCuisine() : String{
        return this.cuisine
    }

    public fun getBudget() : Int{
        return this.budget
    }

    public fun getMaxRating() : Double{
        return this.maxRating
    }

    public fun getMinRating() : Double{
        return this.minRating
    }

    public fun getRestaurant() : ArrayList<String>{
        return this.preferredRestaurants
    }
}