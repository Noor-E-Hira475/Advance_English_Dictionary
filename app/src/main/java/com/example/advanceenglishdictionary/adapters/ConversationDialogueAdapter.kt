package com.example.advanceenglishdictionary.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.advanceenglishdictionary.databinding.ItemDialogueLeftBinding
import com.example.advanceenglishdictionary.databinding.ItemDialogueRightBinding
import com.example.advanceenglishdictionary.models.DialogueLine

class ConversationDialogueAdapter(
    private val lines: List<DialogueLine>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_LEFT = 0
        private const val VIEW_TYPE_RIGHT = 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (lines[position].isLeft) VIEW_TYPE_LEFT else VIEW_TYPE_RIGHT
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == VIEW_TYPE_LEFT) {
            val binding = ItemDialogueLeftBinding.inflate(inflater, parent, false)
            LeftViewHolder(binding)
        } else {
            val binding = ItemDialogueRightBinding.inflate(inflater, parent, false)
            RightViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val line = lines[position]
        if (holder is LeftViewHolder) {
            holder.bind(line)
        } else if (holder is RightViewHolder) {
            holder.bind(line)
        }
    }

    override fun getItemCount(): Int = lines.size

    inner class LeftViewHolder(private val binding: ItemDialogueLeftBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(line: DialogueLine) {
            binding.tvSpeakerName.text = line.name
            binding.tvDialogueText.text = line.text
        }
    }

    inner class RightViewHolder(private val binding: ItemDialogueRightBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(line: DialogueLine) {
            binding.tvSpeakerName.text = line.name
            binding.tvDialogueText.text = line.text
        }
    }
}
