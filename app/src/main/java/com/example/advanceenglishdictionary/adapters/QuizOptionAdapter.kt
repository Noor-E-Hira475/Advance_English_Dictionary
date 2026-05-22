package com.example.advanceenglishdictionary.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.advanceenglishdictionary.R
import com.example.advanceenglishdictionary.databinding.ItemQuizOptionBinding

data class QuizOption(
    val text: String,
    val isCorrect: Boolean,
    var isSelected: Boolean = false,
    var showResult: Boolean = false
)

class QuizOptionAdapter(
    private var options: List<QuizOption>,
    private val onOptionSelected: (QuizOption) -> Unit
) : RecyclerView.Adapter<QuizOptionAdapter.OptionViewHolder>() {

    private var isInteractionEnabled = true

    inner class OptionViewHolder(val binding: ItemQuizOptionBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionViewHolder {
        val binding = ItemQuizOptionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OptionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OptionViewHolder, position: Int) {
        val option = options[position]
        val binding = holder.binding

        binding.tvOptionText.text = option.text

        // Color Logic
        if (option.showResult) {
            when {
                option.isCorrect -> {
                    binding.root.setCardBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.quiz_correct))
                    binding.tvOptionText.setTextColor(ContextCompat.getColor(binding.root.context, android.R.color.white))
                }
                option.isSelected && !option.isCorrect -> {
                    binding.root.setCardBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.quiz_incorrect))
                    binding.tvOptionText.setTextColor(ContextCompat.getColor(binding.root.context, android.R.color.white))
                }
                else -> {
                    binding.root.setCardBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.quiz_option_bg))
                    binding.tvOptionText.setTextColor(ContextCompat.getColor(binding.root.context, R.color.text_primary))
                }
            }
        } else {
            binding.root.setCardBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.quiz_option_bg))
            binding.tvOptionText.setTextColor(ContextCompat.getColor(binding.root.context, R.color.text_primary))
        }

        binding.root.setOnClickListener {
            if (isInteractionEnabled) {
                isInteractionEnabled = false
                option.isSelected = true
                onOptionSelected(option)
            }
        }
    }

    override fun getItemCount(): Int = options.size

    fun updateOptions(newOptions: List<QuizOption>) {
        options = newOptions
        isInteractionEnabled = true
        notifyDataSetChanged()
    }

    fun revealResults() {
        options.forEach { it.showResult = true }
        notifyDataSetChanged()
    }
}
