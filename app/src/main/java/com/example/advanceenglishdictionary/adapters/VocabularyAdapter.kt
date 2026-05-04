package com.example.advanceenglishdictionary.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.advanceenglishdictionary.databinding.ItemVocabularyBinding
import com.example.advanceenglishdictionary.models.VocabularyItem

class VocabularyAdapter(
    private val list: List<VocabularyItem>,
    private val onItemClick: (VocabularyItem) -> Unit
) : RecyclerView.Adapter<VocabularyAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemVocabularyBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemVocabularyBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        holder.binding.tvVocabularyWord.text = item.word

        holder.binding.root.setOnClickListener {
            onItemClick(item)
        }
    }

    override fun getItemCount(): Int = list.size
}