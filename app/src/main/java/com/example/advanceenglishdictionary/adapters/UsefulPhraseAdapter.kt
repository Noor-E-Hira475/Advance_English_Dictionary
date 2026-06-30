package com.example.advanceenglishdictionary.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.advanceenglishdictionary.databinding.ItemUsefulPhraseBinding
import com.example.advanceenglishdictionary.models.UsefulPhrase

class UsefulPhraseAdapter(
    private val phrases: List<UsefulPhrase>
) : RecyclerView.Adapter<UsefulPhraseAdapter.PhraseViewHolder>() {

    inner class PhraseViewHolder(val binding: ItemUsefulPhraseBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhraseViewHolder {
        val binding = ItemUsefulPhraseBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return PhraseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhraseViewHolder, position: Int) {
        val item = phrases[position]
        holder.binding.tvSource.text = item.source
        holder.binding.tvTranslation.text = item.translation
    }

    override fun getItemCount(): Int = phrases.size
}
