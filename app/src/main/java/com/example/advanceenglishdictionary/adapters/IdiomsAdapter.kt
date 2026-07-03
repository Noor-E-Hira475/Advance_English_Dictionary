package com.example.advanceenglishdictionary.adapters

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.advanceenglishdictionary.databinding.ItemIdiomBinding
import com.example.advanceenglishdictionary.extensions.showToast
import com.example.advanceenglishdictionary.models.Idioms

class IdiomsAdapter(
    private val context: Context,
    private val idioms: List<Idioms>
) : RecyclerView.Adapter<IdiomsAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemIdiomBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemIdiomBinding.inflate(
            LayoutInflater.from(context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = idioms[position]
        val binding = holder.binding

        binding.tvIdiomText.text = item.idiom
        binding.tvIdiomMeaning.text = item.meaning
        binding.tvIdiomExample.text = "Example: ${item.example}"

        // Copy Button Click Listener
        binding.btnCopy.setOnClickListener {
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val textToCopy = "Idiom: ${item.idiom}\nMeaning: ${item.meaning}\nExample: ${item.example}"
            val clip = ClipData.newPlainText("Idiom", textToCopy)
            clipboard.setPrimaryClip(clip)

            context.showToast("Idiom copied to clipboard")

            binding.btnCopy.isSelected = true
            Handler(Looper.getMainLooper()).postDelayed({
                binding.btnCopy.isSelected = false
            }, 500)
        }

        // Share Button Click Listener
        binding.btnShare.setOnClickListener {
            val textToShare = "Idiom: ${item.idiom}\nMeaning: ${item.meaning}\nExample: ${item.example}"
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, textToShare)
            }
            context.startActivity(Intent.createChooser(shareIntent, "Share Idiom"))
        }
    }

    override fun getItemCount(): Int = idioms.size
}
