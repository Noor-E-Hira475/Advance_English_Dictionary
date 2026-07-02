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
import com.example.advanceenglishdictionary.databinding.ItemQuoteBinding
import com.example.advanceenglishdictionary.models.Quote
import com.example.advanceenglishdictionary.extensions.showToast

class QuotesAdapter(
    private val context: Context,
    private val quotes: List<Quote>
) : RecyclerView.Adapter<QuotesAdapter.QuoteViewHolder>() {

    inner class QuoteViewHolder(val binding: ItemQuoteBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        val binding = ItemQuoteBinding.inflate(
            LayoutInflater.from(context),
            parent,
            false
        )
        return QuoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        val quote = quotes[position]
        val binding = holder.binding

        binding.tvQuoteText.text = "\"${quote.quote}\""
        binding.tvQuoteAuthor.text = "— ${quote.author}"

        // Copy Button Click Listener
        binding.btnCopy.setOnClickListener {
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val textToCopy = "\"${quote.quote}\"\n— ${quote.author}"
            val clip = ClipData.newPlainText("Quote", textToCopy)
            clipboard.setPrimaryClip(clip)

            context.showToast("Quote copied to clipboard")

            binding.btnCopy.isSelected = true
            Handler(Looper.getMainLooper()).postDelayed({
                binding.btnCopy.isSelected = false
            }, 500)
        }

        // Share Button Click Listener
        binding.btnShare.setOnClickListener {
            val textToShare = "\"${quote.quote}\"\n— ${quote.author}"
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, textToShare)
            }
            context.startActivity(Intent.createChooser(shareIntent, "Share Quote"))
        }
    }

    override fun getItemCount(): Int = quotes.size
}
