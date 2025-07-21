package com.example.movemate.ui.component

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movemate.R
import com.example.movemate.models.VehiclesOption

class AvailableVehiclesAdapter (private val items: List<VehiclesOption>, private val context: Context) : RecyclerView.Adapter<AvailableVehiclesAdapter.SlideInViewHolder>() {

    inner class SlideInViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val titleText: TextView = itemView.findViewById(R.id.titleText)
        val descText: TextView = itemView.findViewById(R.id.descText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlideInViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.available_vehicles_item, parent, false)
        return SlideInViewHolder(view)
    }

    override fun onBindViewHolder(holder: SlideInViewHolder, position: Int) {
        val item = items[position]
        holder.imageView.setImageResource(item.imageResId)
        holder.titleText.text = item.title
        holder.descText.text = item.description

        animateSlideIn(holder.itemView, position)

        animateImageSlideIn(holder.imageView, position)
    }

    override fun getItemCount(): Int = items.size

    private fun animateSlideIn(view: View, position: Int) {
        view.translationX = view.context.resources.displayMetrics.widthPixels.toFloat()
        view.alpha = 0f
        view.animate()
            .translationX(0f)
            .alpha(1f)
            .setInterpolator(DecelerateInterpolator())
            .setDuration(500)
            .setStartDelay((position * 30).toLong())
            .start()
    }

    private fun animateImageSlideIn(view: View, position: Int) {
        view.translationX = view.context.resources.displayMetrics.widthPixels.toFloat()
        view.alpha = 0f
        view.animate()
            .translationX(0f)
            .alpha(1f)
            .setInterpolator(DecelerateInterpolator())
            .setDuration(900)
            .setStartDelay((position * 30).toLong())
            .start()
    }
}