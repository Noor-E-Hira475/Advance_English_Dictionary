package com.example.advanceenglishdictionary.adapters;

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context;
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView;

import com.example.advanceenglishdictionary.databinding.ItemsVocaWordsBinding;
import com.example.advanceenglishdictionary.extensions.showToast
import com.example.advanceenglishdictionary.models.VocaWord;


class VocaWordAdapter(
    private val context: Context,
    private val vocaWords: List<VocaWord>
) : RecyclerView.Adapter<VocaWordAdapter.VocaWordViewHolder>() {

    inner class VocaWordViewHolder(val binding:ItemsVocaWordsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VocaWordViewHolder {
        val binding = ItemsVocaWordsBinding.inflate(LayoutInflater.from(context), parent, false)
        return VocaWordViewHolder(binding)
    }

    override fun getItemCount(): Int = vocaWords.size
    override fun onBindViewHolder(holder: VocaWordViewHolder, position: Int) {
        val vocaWord = vocaWords[position]
        val binding = holder.binding

        binding.tvWordValue.text = vocaWord.keyword
        binding.tvTypeValue.text = vocaWord.type
        binding.tvDefinitionValue.text = vocaWord.definition
        binding.tvExampleValue.text = vocaWord.example

        // favorite icon
        binding.btnFavorite.isSelected = vocaWord.favorite == 1
        binding.btnFavorite.setOnClickListener {
            binding.btnFavorite.isSelected = !binding.btnFavorite.isSelected
            if(binding.btnFavorite.isSelected){
                context.showToast("Added to Favorites")
            } else {
                context.showToast("Removed form favorites")
            }
        }

        // copy icon
        binding.btnCopy.setOnClickListener {
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val textToCopy = "Word: ${vocaWord.keyword}\nDefinition: ${vocaWord.definition}\nExample: ${vocaWord.example}"
            val clip = ClipData.newPlainText("Proverb", textToCopy)
            clipboard.setPrimaryClip(clip)

            binding.btnCopy.isSelected = true
            Handler(Looper.getMainLooper()).postDelayed({
                binding.btnCopy.isSelected = false
            }, 500)
        }

        // speaker icon
        binding.btnSound.setOnClickListener {
            // Prevent multiple clicks while "playing"
            if (binding.btnSound.isSelected) return@setOnClickListener

            // Show icon as selected
            binding.btnSound.isSelected = true

            // Simulate audio playing for 2 seconds
            Handler(Looper.getMainLooper()).postDelayed({
                binding.btnSound.isSelected = false
            }, 2000) // 2 seconds, you can adjust
        }
    }
}
