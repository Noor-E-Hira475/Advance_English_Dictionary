package com.example.advanceenglishdictionary.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.advanceenglishdictionary.databinding.ItemDefinitionBinding
import com.example.advanceenglishdictionary.models.api.Definition

/**
 * Renders a flat list of [Definition] objects inside one [MeaningAdapter] row.
 * Each row shows: definition text, optional example, optional synonyms, optional antonyms.
 */
class DefinitionAdapter : ListAdapter<Definition, DefinitionAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDefinitionBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemDefinitionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Definition) {
            binding.tvDefinition.text = item.definition

            // Example sentence
            if (!item.example.isNullOrBlank()) {
                binding.tvExample.text = "\"${item.example}\""
                binding.tvExample.visibility = android.view.View.VISIBLE
            } else {
                binding.tvExample.visibility = android.view.View.GONE
            }

            // Synonyms  (green)
            if (item.synonyms.isNotEmpty()) {
                binding.tvSynonyms.text = "Synonyms: ${item.synonyms.joinToString(", ")}"
                binding.tvSynonyms.visibility = android.view.View.VISIBLE
            } else {
                binding.tvSynonyms.visibility = android.view.View.GONE
            }

            // Antonyms  (red)
            if (item.antonyms.isNotEmpty()) {
                binding.tvAntonyms.text = "Antonyms: ${item.antonyms.joinToString(", ")}"
                binding.tvAntonyms.visibility = android.view.View.VISIBLE
            } else {
                binding.tvAntonyms.visibility = android.view.View.GONE
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<Definition>() {
        override fun areItemsTheSame(oldItem: Definition, newItem: Definition) =
            oldItem.definition == newItem.definition
        override fun areContentsTheSame(oldItem: Definition, newItem: Definition) =
            oldItem == newItem
    }
}
