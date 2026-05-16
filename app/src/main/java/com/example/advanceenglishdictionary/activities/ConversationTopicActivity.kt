package com.example.advanceenglishdictionary.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.advanceenglishdictionary.adapters.ConversationTopicAdapter
import com.example.advanceenglishdictionary.dao.ConversationDao
import com.example.advanceenglishdictionary.databinding.ActivityConversationTopicBinding

class ConversationTopicActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConversationTopicBinding
    private lateinit var conversationDao: ConversationDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConversationTopicBinding.inflate(layoutInflater)
        setContentView(binding.root)

        conversationDao = ConversationDao(this)

        val categoryName = intent.getStringExtra("CATEGORY_NAME") ?: ""
        
        loadTopics(categoryName)
    }

    private fun loadTopics(category: String) {
        val topics = conversationDao.getTopicsByCategory(category)
        binding.rvTopics.layoutManager = LinearLayoutManager(this)
        binding.rvTopics.adapter = ConversationTopicAdapter(topics) { topic ->
            val intent = Intent(this, ConversationDialogueActivity::class.java).apply {
                putExtra("TOPIC_TITLE", topic.title)
                putExtra("CONVERSATION_TEXT", topic.conversationText)
            }
            startActivity(intent)
        }
    }
}
