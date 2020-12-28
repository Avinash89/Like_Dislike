package com.example.samplecard.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mvvmkotlinexample.R
import com.example.samplecard.model.Result

class CardStackAdapter(
    private var spots: List<Result>
) : RecyclerView.Adapter<CardStackAdapter.ViewHolder>() {

    private var currentPosition = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_spot, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val spot = spots[position]
        holder.name.text = spot.name.first
        Glide.with(holder.image)
            .load(spot.picture.large)
            .into(holder.image)
        holder.itemView.setOnClickListener { v ->
            Toast.makeText(v.context, spot.name.first, Toast.LENGTH_SHORT).show()
        }
        setPosition(position)
    }

    override fun getItemCount(): Int {
        return spots.size
    }

    fun setSpots(spots: List<Result>) {
        this.spots = spots
    }

    fun getPosition(): Int {
        return currentPosition
    }

    fun setPosition(position: Int) {
        this.currentPosition = position
    }

    fun getSpots(): List<Result> {
        return spots
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.item_name)
        var image: ImageView = view.findViewById(R.id.item_image)
    }

}