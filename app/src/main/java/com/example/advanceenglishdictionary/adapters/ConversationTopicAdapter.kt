package com.example.advanceenglishdictionary.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.advanceenglishdictionary.databinding.ItemConversationTopicBinding
import com.example.advanceenglishdictionary.models.Conversation

class ConversationTopicAdapter(
    private val topics: List<Conversation>,
    private val onItemClick: (Conversation) -> Unit
) : RecyclerView.Adapter<ConversationTopicAdapter.TopicViewHolder>() {

    inner class TopicViewHolder(
        private val binding: ItemConversationTopicBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(topic: Conversation) {
            binding.tvTopicTitle.text = topic.title
            binding.root.setOnClickListener { onItemClick(topic) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder {
        val binding = ItemConversationTopicBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TopicViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) {
        holder.bind(topics[position])
    }

    override fun getItemCount(): Int = topics.size
}
