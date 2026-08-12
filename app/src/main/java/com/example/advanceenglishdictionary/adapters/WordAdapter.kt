package com.example.advanceenglishdictionary.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.advanceenglishdictionary.databinding.ItemWordBinding
import com.example.advanceenglishdictionary.models.WordKey

class WordAdapter(
    private val onWordClick: (WordKey) -> Unit
) : ListAdapter<WordKey, WordAdapter.WordViewHolder>(WordDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val binding = ItemWordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class WordViewHolder(private val binding: ItemWordBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: WordKey) {
            binding.tvWord.text = item.word
            binding.root.setOnClickListener {
                onWordClick(item)
            }
        }
    }

    class WordDiffCallback : DiffUtil.ItemCallback<WordKey>() {
        override fun areItemsTheSame(oldItem: WordKey, newItem: WordKey): Boolean {
            return oldItem.idRef == newItem.idRef
        }

        override fun areContentsTheSame(oldItem: WordKey, newItem: WordKey): Boolean {
            return oldItem == newItem
        }
    }
}
