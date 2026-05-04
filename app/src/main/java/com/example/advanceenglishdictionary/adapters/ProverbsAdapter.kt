package com.example.advanceenglishdictionary.adapters

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.advanceenglishdictionary.models.Proverbs
import com.example.advanceenglishdictionary.databinding.ItemProverbsBinding
import com.example.advanceenglishdictionary.extensions.showToast

class ProverbsAdapter(
    private val context: Context,
    private val proverbs: List<Proverbs>
) : RecyclerView.Adapter<ProverbsAdapter.ProverbViewHolder>() {

    inner class ProverbViewHolder(val binding: ItemProverbsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProverbViewHolder {
        val binding = ItemProverbsBinding.inflate(LayoutInflater.from(context), parent, false)
        return ProverbViewHolder(binding)
    }

    override fun getItemCount(): Int = proverbs.size

    override fun onBindViewHolder(holder: ProverbViewHolder, position: Int) {
        val proverb = proverbs[position]
        val binding = holder.binding

        // --- Set text ---
        binding.tvTitleValue.text = proverb.title
        binding.tvDescriptionValue.text = proverb.description

        // favorite icon
        binding.btnFavorite.isSelected = proverb.favorite == 1
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
            val textToCopy = "Proverb: ${proverb.title}\nDescription: ${proverb.description}"
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