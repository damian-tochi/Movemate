package com.example.movemate.ui.component

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movemate.R
import com.example.movemate.models.SearchHistory

class SearchHistoryAdapter(private val items: List<SearchHistory>) :
    RecyclerView.Adapter<SearchHistoryAdapter.SlideInViewHolder>() {

    private var filteredList: List<SearchHistory> = items.toList()

    inner class SlideInViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleText: TextView = itemView.findViewById(R.id.titleText)
        val trackText: TextView = itemView.findViewById(R.id.trackIdText)
        val fromText: TextView = itemView.findViewById(R.id.fromText)
        val toText: TextView = itemView.findViewById(R.id.toText)
        val line: View = itemView.findViewById(R.id.bottomLine)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlideInViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_history_item, parent, false)
        return SlideInViewHolder(view)
    }

    override fun onBindViewHolder(holder: SlideInViewHolder, position: Int) {
        val item = filteredList[position]
        holder.titleText.text = item.title
        holder.trackText.text = item.trackId
        holder.fromText.text = item.from
        holder.toText.text = item.to

        holder.line.visibility = if (position == filteredList.size - 1) View.GONE else View.VISIBLE

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

    fun updateList(newList: List<SearchHistory>) {
        val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize() = filteredList.size
            override fun getNewListSize() = newList.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return filteredList[oldItemPosition].trackId == newList[newItemPosition].trackId
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return filteredList[oldItemPosition] == newList[newItemPosition]
            }
        })
        filteredList = newList
        diffResult.dispatchUpdatesTo(this)
        notifyDataSetChanged()
    }

    fun filter(query: String) {
        val filtered = if (query.isBlank()) {
            items
        } else {
            items.filter { it.trackId.contains(query, ignoreCase = true) }
        }
        updateList(filtered)
    }
}
