package com.example.movemate.ui.component

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.movemate.R
import com.example.movemate.models.ShipmentsHistory


class ShipmentHistoryAdapter(private val items: List<ShipmentsHistory>) :
    RecyclerView.Adapter<ShipmentHistoryAdapter.SlideInViewHolder>() {

    private var filteredList: List<ShipmentsHistory> = items.toList()

    inner class SlideInViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val statusImage: ImageView = itemView.findViewById(R.id.statusImg)
        val statusText: TextView = itemView.findViewById(R.id.statusText)
        val titleText: TextView = itemView.findViewById(R.id.titleText)
        val descriptionText: TextView = itemView.findViewById(R.id.descriptionText)
        val costText: TextView = itemView.findViewById(R.id.costText)
        val dateText: TextView = itemView.findViewById(R.id.dateText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlideInViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.shipment_history_item, parent, false)
        return SlideInViewHolder(view)
    }

    override fun onBindViewHolder(holder: SlideInViewHolder, position: Int) {
        val item = filteredList[position]

        holder.statusText.text = item.status
        if (item.status == "pending") {
            holder.statusText.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.colorOrange))
            holder.statusImage.setImageResource(R.drawable.ic_pending_arrow)
        }
        if (item.status == "in-progress") {
            holder.statusText.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.colorGreen))
            holder.statusImage.setImageResource(R.drawable.ic_progress_arrows)
        }
        if (item.status == "loading") {
            holder.statusText.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.colorBlue))
            holder.statusImage.setImageResource(R.drawable.ic_loading_arrow)
        }
        if (item.status == "completed") {
            holder.statusText.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.colorGreenDarker))
            holder.statusImage.setImageResource(R.drawable.ic_done_ring)
        }
        if (item.status == "cancelled") {
            holder.statusText.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.colorError))
            holder.statusImage.setImageResource(R.drawable.ic_cancelled)
        }

        holder.titleText.text = item.title
        holder.descriptionText.text = "Your delivery, ${item.trackId}\n from ${item.from}, is arriving today!"
        holder.costText.text = "\$${item.amount} USD"
        holder.dateText.text = item.date

        animateSlideIn(holder.itemView, position)
    }

    override fun getItemCount(): Int = filteredList.size

    private fun animateSlideIn(view: View, position: Int) {
        view.translationY = view.context.resources.displayMetrics.heightPixels * 0.3f
        view.alpha = 0f

        view.animate()
            .translationY(0f)
            .alpha(1f)
            .setInterpolator(DecelerateInterpolator())
            .setDuration(500)
            .setStartDelay((position * 40).toLong())
            .start()
    }

    fun filter(query: String) {
        filteredList = if (query.isBlank() || query == "All") {
            items
        } else {
            items.filter { it.status.contains(query, ignoreCase = true) }
        }
        notifyDataSetChanged()
    }
}