package com.example.advanceenglishdictionary.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.advanceenglishdictionary.databinding.ItemWordDefinitionBinding
import com.example.advanceenglishdictionary.models.WordDescription
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class WordDefinitionAdapter(
    private val onRelatedWordClick: ((String) -> Unit)? = null
) : ListAdapter<WordDescription, WordDefinitionAdapter.DefinitionViewHolder>(DefinitionDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefinitionViewHolder {
        val binding = ItemWordDefinitionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DefinitionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DefinitionViewHolder, position: Int) {
        holder.bind(getItem(position), position + 1)
    }

    inner class DefinitionViewHolder(private val binding: ItemWordDefinitionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: WordDescription, senseNumber: Int) {
            binding.tvSenseNumber.text = "Sense $senseNumber"

            // Category / Part of Speech formatting
            val formattedCategory = when (item.category?.lowercase()?.trim()) {
                "n" -> "noun"
                "v" -> "verb"
                "a" -> "adjective"
                "r" -> "adverb"
                "s" -> "adjective satellite"
                else -> item.category ?: "general"
            }
            binding.tvCategory.text = formattedCategory

            // 1. Definition
            if (!item.definition.isNullOrBlank()) {
                binding.layoutDefinition.visibility = View.VISIBLE
                binding.tvDefinition.text = item.definition.trim()
            } else {
                binding.layoutDefinition.visibility = View.GONE
            }

            // 2. Examples
            if (!item.examples.isNullOrBlank()) {
                binding.layoutExamples.visibility = View.VISIBLE
                val cleanedExamples = item.examples.trim().removeSurrounding("\"", "\"")
                binding.tvExamples.text = "\"$cleanedExamples\""
            } else {
                binding.layoutExamples.visibility = View.GONE
            }

            // 3. Synonyms
            setupChips(binding.layoutSynonyms, binding.cgSynonyms, item.synonyms)

            // 4. Antonyms
            setupChips(binding.layoutAntonyms, binding.cgAntonyms, item.antonyms)

            // 5. Hyponyms
            setupChips(binding.layoutHyponyms, binding.cgHyponyms, item.hyponyms)

            // 6. Instance Hyponyms
            setupChips(binding.layoutInstanceHyponyms, binding.cgInstanceHyponyms, item.instanceHyponyms)

            // 7. Hypernyms
            setupChips(binding.layoutHypernyms, binding.cgHypernyms, item.hypernyms)

            // 8. Instance Hypernyms
            setupChips(binding.layoutInstanceHypernyms, binding.cgInstanceHypernyms, item.instanceHypernyms)

            // 9. Part Holonyms
            setupChips(binding.layoutPartHolonyms, binding.cgPartHolonyms, item.partHolonyms)

            // 10. Member Holonyms
            setupChips(binding.layoutMemberHolonyms, binding.cgMemberHolonyms, item.memberHolonyms)

            // 11. Substance Holonyms
            setupChips(binding.layoutSubstanceHolonyms, binding.cgSubstanceHolonyms, item.substanceHolonyms)

            // 12. Part Meronyms
            setupChips(binding.layoutPartMeronyms, binding.cgPartMeronyms, item.partMeronyms)

            // 13. Member Meronyms
            setupChips(binding.layoutMemberMeronyms, binding.cgMemberMeronyms, item.memberMeronyms)

            // 14. Substance Meronyms
            setupChips(binding.layoutSubstanceMeronyms, binding.cgSubstanceMeronyms, item.substanceMeronyms)

            // 15. Similar Words
            setupChips(binding.layoutSimilar, binding.cgSimilar, item.similar)

            // 16. Also
            setupChips(binding.layoutAlso, binding.cgAlso, item.also)
        }

        private fun setupChips(
            layout: View,
            chipGroup: ChipGroup,
            rawText: String?
        ) {
            chipGroup.removeAllViews()
            if (rawText.isNullOrBlank()) {
                layout.visibility = View.GONE
                return
            }

            val words = rawText.split(",", ";").map { it.trim() }.filter { it.isNotEmpty() }
            if (words.isEmpty()) {
                layout.visibility = View.GONE
                return
            }

            layout.visibility = View.VISIBLE
            val context = chipGroup.context
            for (w in words) { // Render ALL available items
                val chip = Chip(context).apply {
                    text = w
                    isClickable = onRelatedWordClick != null
                    setOnClickListener {
                        onRelatedWordClick?.invoke(w)
                    }
                }
                chipGroup.addView(chip)
            }
        }
    }

    class DefinitionDiffCallback : DiffUtil.ItemCallback<WordDescription>() {
        override fun areItemsTheSame(oldItem: WordDescription, newItem: WordDescription): Boolean {
            return oldItem.id == newItem.id && oldItem.definition == newItem.definition
        }

        override fun areContentsTheSame(oldItem: WordDescription, newItem: WordDescription): Boolean {
            return oldItem == newItem
        }
    }
}
