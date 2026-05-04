package com.example.advanceenglishdictionary.adapters

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.advanceenglishdictionary.databinding.ItemIVerbBinding
import com.example.advanceenglishdictionary.extensions.showToast
import com.example.advanceenglishdictionary.models.IVerb

class IVerbAdapter(
    private val context: Context,
    private val verbs: List<IVerb>
) : RecyclerView.Adapter<IVerbAdapter.IVerbViewHolder>() {

    inner class IVerbViewHolder(val binding: ItemIVerbBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IVerbViewHolder {
        val binding = ItemIVerbBinding.inflate(LayoutInflater.from(context), parent, false)
        return IVerbViewHolder(binding)
    }

    override fun getItemCount(): Int = verbs.size

    override fun onBindViewHolder(holder: IVerbViewHolder, position: Int) {

        val verb = verbs[position]
        val binding = holder.binding

        // --- Set text values ---
        binding.tvBaseFormValue.text = verb.baseForm
        binding.tvPastSimpleValue.text = verb.pastSimple
        binding.tvPastPartValue.text = verb.pastPart
        binding.tvPerson3rdValue.text = verb.person3rd
        binding.tvGerundValue.text = verb.gerund
        binding.tvDefinitionValue.text = verb.definition

        // favorite icon
        binding.btnFavorite.isSelected = verb.favorite == 1
        binding.btnFavorite.setOnClickListener {
            binding.btnFavorite.isSelected = !binding.btnFavorite.isSelected

            if (binding.btnFavorite.isSelected) {
                context.showToast("Added to Favorites")
            } else {
                context.showToast("Removed from favorites")
            }
        }

        // copy icon
        binding.btnCopy.setOnClickListener {

            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

            val textToCopy =
                "Base Form: ${verb.baseForm}\n" +
                        "Past Simple: ${verb.pastSimple}\n" +
                        "Past Participle: ${verb.pastPart}\n" +
                        "3rd Person: ${verb.person3rd}\n" +
                        "Gerund: ${verb.gerund}\n" +
                        "Definition: ${verb.definition}"

            val clip = ClipData.newPlainText("Verb", textToCopy)
            clipboard.setPrimaryClip(clip)

            binding.btnCopy.isSelected = true
            Handler(Looper.getMainLooper()).postDelayed({
                binding.btnCopy.isSelected = false
            }, 500)
        }

        // speaker icon
        binding.btnSound.setOnClickListener {

            if (binding.btnSound.isSelected) return@setOnClickListener

            binding.btnSound.isSelected = true

            Handler(Looper.getMainLooper()).postDelayed({
                binding.btnSound.isSelected = false
            }, 2000)
        }
    }
}