package com.example.advanceenglishdictionary.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.advanceenglishdictionary.databinding.ItemOnlineDictionaryBinding
import com.example.advanceenglishdictionary.models.api.DictionaryResponse

/**
 * Top-level adapter for the Online Dictionary results list.
 * Each item is a [DictionaryResponse] (one word entry from the API).
 * Nested inside each card: [MeaningAdapter] → [DefinitionAdapter].
 */
class OnlineDictionaryAdapter :
    ListAdapter<DictionaryResponse, OnlineDictionaryAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemOnlineDictionaryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemOnlineDictionaryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val meaningAdapter = MeaningAdapter()

        init {
            binding.rvMeanings.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = meaningAdapter
                isNestedScrollingEnabled = false
            }
        }

        fun bind(item: DictionaryResponse) {
            // Word title
            binding.tvWord.text = item.word

            // Phonetic — use first non-null phonetic text if the top-level field is null
            val phoneticText = item.phonetic
                ?: item.phonetics.firstOrNull { !it.text.isNullOrBlank() }?.text
            if (!phoneticText.isNullOrBlank()) {
                binding.tvPhonetic.text = phoneticText
                binding.tvPhonetic.visibility = View.VISIBLE
            } else {
                binding.tvPhonetic.visibility = View.GONE
            }

            // Origin
            if (!item.origin.isNullOrBlank()) {
                binding.tvOriginLabel.visibility = View.VISIBLE
                binding.tvOrigin.text = item.origin
                binding.tvOrigin.visibility = View.VISIBLE
            } else {
                binding.tvOriginLabel.visibility = View.GONE
                binding.tvOrigin.visibility = View.GONE
            }

            // Meanings (nested adapter)
            meaningAdapter.submitList(item.meanings)
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<DictionaryResponse>() {
        override fun areItemsTheSame(oldItem: DictionaryResponse, newItem: DictionaryResponse) =
            oldItem.word == newItem.word
        override fun areContentsTheSame(oldItem: DictionaryResponse, newItem: DictionaryResponse) =
            oldItem == newItem
    }
}
