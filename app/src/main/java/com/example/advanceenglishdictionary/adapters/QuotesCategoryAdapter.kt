package com.example.advanceenglishdictionary.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.advanceenglishdictionary.R
import com.example.advanceenglishdictionary.databinding.ItemQuoteCategoryBinding
import java.util.Locale

class QuotesCategoryAdapter(
    private val categories: List<String>,
    private val onCategorySelected: (String) -> Unit
) : RecyclerView.Adapter<QuotesCategoryAdapter.ViewHolder>() {

    private var selectedPosition = 0

    inner class ViewHolder(val binding: ItemQuoteCategoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemQuoteCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val categoryKey = categories[position]
        val context = holder.binding.root.context

        // Capitalize category name for display
        holder.binding.tvCategoryName.text = categoryKey.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
        }

        // Apply background and text color based on selection state
        if (position == selectedPosition) {
            holder.binding.tvCategoryName.setBackgroundResource(R.drawable.bg_pill_selected)
            holder.binding.tvCategoryName.setTextColor(
                ContextCompat.getColor(context, R.color.on_primary)
            )
        } else {
            holder.binding.tvCategoryName.setBackgroundResource(R.drawable.bg_pill)
            holder.binding.tvCategoryName.setTextColor(
                ContextCompat.getColor(context, R.color.text_primary)
            )
        }

        holder.binding.root.setOnClickListener {
            val previousSelected = selectedPosition
            selectedPosition = holder.adapterPosition
            notifyItemChanged(previousSelected)
            notifyItemChanged(selectedPosition)
            onCategorySelected(categoryKey)
        }
    }

    override fun getItemCount(): Int = categories.size
}
