package com.example.advanceenglishdictionary.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.advanceenglishdictionary.models.CommonExpressionsDetail
import com.example.advanceenglishdictionary.databinding.ItemCommonExpressionsDetailBinding

class CommonExpressionsDetailAdapter(
    private val items: List<CommonExpressionsDetail>
) : RecyclerView.Adapter<CommonExpressionsDetailAdapter.DetailViewHolder>() {

    inner class DetailViewHolder(
        private val binding: ItemCommonExpressionsDetailBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CommonExpressionsDetail) {
            binding.textViewPhrase.text = item.phrase
            binding.textViewCategoriesDescription.text = item.description

            // Speaker icons ignored (no voice functionality)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        val binding = ItemCommonExpressionsDetailBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}