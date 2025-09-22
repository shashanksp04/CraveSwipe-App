package com.example.craveswipev11

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SavedPlacesAdapter(private val places: List<RecommendationsActivity.SavedPlace>) :
    RecyclerView.Adapter<SavedPlacesAdapter.PlaceViewHolder>() {

    class PlaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val placeName: TextView = itemView.findViewById(R.id.placeName)
        val placeRating: RatingBar = itemView.findViewById(R.id.placeRating)
        val placeInfo: TextView = itemView.findViewById(R.id.placeInfo)
        val placeImage: ImageView = itemView.findViewById(R.id.placeImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_saved_place, parent, false)
        return PlaceViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        val place = places[position]

        holder.placeName.text = place.name
        holder.placeRating.rating = place.rating.toFloat()
        holder.placeInfo.text = "${place.price} · ${place.distance}"
        holder.placeImage.setImageResource(R.drawable.placeholder_image) // Placeholder image
    }

    override fun getItemCount(): Int = places.size
}
