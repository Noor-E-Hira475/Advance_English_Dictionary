package com.example.advanceenglishdictionary.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.advanceenglishdictionary.databinding.ItemLanguageBinding
import com.example.advanceenglishdictionary.models.Language

class LanguageAdapter(
    private val languages: List<Language>,
    private val selectedDbColumn: String,
    private val onLanguageSelected: (Language) -> Unit
) : RecyclerView.Adapter<LanguageAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemLanguageBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLanguageBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = languages[position]
        val binding = holder.binding

        binding.ivFlag.setImageResource(item.flagResId)
        binding.tvLanguageName.text = item.displayName

        if (item.dbColumnName.equals(selectedDbColumn, ignoreCase = true)) {
            binding.ivSelected.visibility = View.VISIBLE
        } else {
            binding.ivSelected.visibility = View.GONE
        }

        binding.root.setOnClickListener {
            onLanguageSelected(item)
        }
    }

    override fun getItemCount(): Int = languages.size
}
