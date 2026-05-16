package com.example.advanceenglishdictionary.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.advanceenglishdictionary.adapters.ConversationDialogueAdapter
import com.example.advanceenglishdictionary.databinding.ActivityConversationDialogueBinding
import com.example.advanceenglishdictionary.models.DialogueLine

class ConversationDialogueActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConversationDialogueBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConversationDialogueBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val title = intent.getStringExtra("TOPIC_TITLE") ?: "Dialogue"
        val rawText = intent.getStringExtra("CONVERSATION_TEXT") ?: ""

        setupDialogue(rawText)
    }

    private fun setupDialogue(rawText: String) {
        val dialogueLines = parseDialogue(rawText)
        binding.rvDialogue.layoutManager = LinearLayoutManager(this)
        binding.rvDialogue.adapter = ConversationDialogueAdapter(dialogueLines)
    }

    private fun parseDialogue(rawText: String): List<DialogueLine> {
        val lines = mutableListOf<DialogueLine>()
        // Split by ~
        val parts = rawText.split("~")
        
        parts.forEachIndexed { index, part ->
            if (part.isNotBlank()) {
                // Split by first :
                val colonIndex = part.indexOf(":")
                if (colonIndex != -1) {
                    val name = part.substring(0, colonIndex).trim()
                    val text = part.substring(colonIndex + 1).trim()
                    
                    // First line start (Left), second line end (Right), and so on
                    val isLeft = index % 2 == 0
                    
                    lines.add(DialogueLine(name, text, isLeft))
                }
            }
        }
        return lines
    }
}
