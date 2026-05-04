package com.example.advanceenglishdictionary.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.advanceenglishdictionary.models.CommonPhrase
import com.example.advanceenglishdictionary.databinding.ItemCommonPhraseBinding

class CommonPhrasesAdapter(
    private val phrases: List<CommonPhrase>,
    private val onItemClick: (CommonPhrase) -> Unit // click callback
) : RecyclerView.Adapter<CommonPhrasesAdapter.PhraseViewHolder>() {

    inner class PhraseViewHolder(val binding: ItemCommonPhraseBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhraseViewHolder {
        val binding = ItemCommonPhraseBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PhraseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhraseViewHolder, position: Int) {
        val phrase = phrases[position]

        // Set category text
        holder.binding.textViewCategories.text = phrase.categories

        // Make the item clickable
        holder.binding.root.setOnClickListener {
            onItemClick(phrase)
        }
    }

    override fun getItemCount(): Int = phrases.size
}