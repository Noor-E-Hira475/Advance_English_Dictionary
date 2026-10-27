package com.example.advanceenglishdictionary.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.advanceenglishdictionary.databinding.ItemMeaningBinding
import com.example.advanceenglishdictionary.models.api.Meaning

/**
 * Renders a list of [Meaning] objects (partOfSpeech + nested definitions).
 * Used inside [OnlineDictionaryAdapter] for each word card.
 */
class MeaningAdapter : ListAdapter<Meaning, MeaningAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMeaningBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemMeaningBinding) :
        RecyclerView.ViewHolder(binding.root) {

        // Each ViewHolder owns its own DefinitionAdapter to avoid cross-item contamination
        private val definitionAdapter = DefinitionAdapter()

        init {
            binding.rvDefinitions.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = definitionAdapter
                // Already nested inside an outer scroll — disable its own scroll
                isNestedScrollingEnabled = false
            }
        }

        fun bind(item: Meaning) {
            binding.tvPartOfSpeech.text = item.partOfSpeech
            definitionAdapter.submitList(item.definitions)
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<Meaning>() {
        override fun areItemsTheSame(oldItem: Meaning, newItem: Meaning) =
            oldItem.partOfSpeech == newItem.partOfSpeech
        override fun areContentsTheSame(oldItem: Meaning, newItem: Meaning) =
            oldItem == newItem
    }
}
