package com.example.craveswipev11.cuisineRelated

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.craveswipev11.R
import android.view.LayoutInflater

data class Cuisine(val imageResId: Int, val name: String)

class CuisineAdapter(
    private val cuisines: List<Cuisine>,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<CuisineAdapter.CuisineViewHolder>() {

    inner class CuisineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.cuisineImage)
        val textView: TextView = itemView.findViewById(R.id.cuisineText)

        init {
            itemView.setOnClickListener {
                onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CuisineViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cuisine, parent, false)
        return CuisineViewHolder(view)
    }

    override fun onBindViewHolder(holder: CuisineViewHolder, position: Int) {
        val cuisine = cuisines[position]
        holder.imageView.setImageResource(cuisine.imageResId)
        holder.textView.text = cuisine.name
    }

    override fun getItemCount(): Int = cuisines.size
}
