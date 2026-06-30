package com.example.advanceenglishdictionary.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.advanceenglishdictionary.databinding.ItemUsefulPhraseCategoryBinding
import com.example.advanceenglishdictionary.models.UsefulPhraseCategory

class UsefulPhraseCategoryAdapter(
    private val categories: List<UsefulPhraseCategory>,
    private val onItemClick: (UsefulPhraseCategory) -> Unit
) : RecyclerView.Adapter<UsefulPhraseCategoryAdapter.CategoryViewHolder>() {

    inner class CategoryViewHolder(
        private val binding: ItemUsefulPhraseCategoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(category: UsefulPhraseCategory) {
            binding.tvCategoryName.text = category.name
            binding.ivCategoryIcon.setImageResource(category.iconResId)

            binding.root.setOnClickListener {
                onItemClick(category)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemUsefulPhraseCategoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount(): Int = categories.size
}
