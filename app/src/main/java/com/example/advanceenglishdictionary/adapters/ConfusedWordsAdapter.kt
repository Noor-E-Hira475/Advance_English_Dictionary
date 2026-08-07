package com.example.advanceenglishdictionary.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.advanceenglishdictionary.databinding.ItemConfusedWordBinding
import com.example.advanceenglishdictionary.models.ConfusedWord

class ConfusedWordsAdapter(
    private var words: List<ConfusedWord> = emptyList(),
    private val onItemClick: (ConfusedWord, Int) -> Unit
) : RecyclerView.Adapter<ConfusedWordsAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemConfusedWordBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemConfusedWordBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = words[position]
        holder.binding.tvWordPair.text = item.pair.joinToString(", ")
        holder.binding.root.setOnClickListener {
            onItemClick(item, position)
        }
    }

    override fun getItemCount(): Int = words.size

    fun updateData(newWords: List<ConfusedWord>) {
        words = newWords
        notifyDataSetChanged()
    }
}
