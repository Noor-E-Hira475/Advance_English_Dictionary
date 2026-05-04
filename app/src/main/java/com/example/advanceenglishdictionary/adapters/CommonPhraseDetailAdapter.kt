package com.example.advanceenglishdictionary.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.advanceenglishdictionary.models.CommonPhraseDetail
import com.example.advanceenglishdictionary.databinding.ItemCommonPhraseDetailBinding

class CommonPhraseDetailAdapter(
    private val phrases: List<CommonPhraseDetail>
) : RecyclerView.Adapter<CommonPhraseDetailAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemCommonPhraseDetailBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCommonPhraseDetailBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = phrases[position]
        holder.binding.textViewPhrases.text = item.phrase
    }

    override fun getItemCount(): Int = phrases.size
}