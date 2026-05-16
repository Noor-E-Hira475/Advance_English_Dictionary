package com.example.advanceenglishdictionary.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.advanceenglishdictionary.R
import com.example.advanceenglishdictionary.adapters.ConversationAdapter
import com.example.advanceenglishdictionary.models.ConversationCategory

class ConversationActivity : AppCompatActivity() {

    private lateinit var rvConversation: RecyclerView
    private lateinit var adapter: ConversationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conversation)

        rvConversation = findViewById(R.id.rvConversation)

        rvConversation.layoutManager = GridLayoutManager(this, 2)

        val categories = listOf(
            ConversationCategory(1, getString(R.string.cat_eating), R.drawable.ic_eating_card),
            ConversationCategory(2, getString(R.string.cat_emotion), R.drawable.ic_emotion_card),
            ConversationCategory(3, getString(R.string.cat_fashion), R.drawable.ic_fashion_card),
            ConversationCategory(4, getString(R.string.cat_friendship), R.drawable.ic_friendship_card),
            ConversationCategory(5, getString(R.string.cat_health), R.drawable.ic_health_card),
            ConversationCategory(6, getString(R.string.cat_housing), R.drawable.ic_housing_card),
            ConversationCategory(7, getString(R.string.cat_life), R.drawable.ic_life_card),
            ConversationCategory(8, getString(R.string.cat_memory), R.drawable.ic_memory_card),
            ConversationCategory(9, getString(R.string.cat_shopping), R.drawable.ic_shopping_card),
            ConversationCategory(10, getString(R.string.cat_time), R.drawable.ic_time_card),
            ConversationCategory(11, getString(R.string.cat_travel), R.drawable.ic_travel_card),
            ConversationCategory(12, getString(R.string.cat_vacation), R.drawable.ic_vacation_card),
            ConversationCategory(13, getString(R.string.cat_weather), R.drawable.ic_weather_card),
            ConversationCategory(14, getString(R.string.cat_work), R.drawable.ic_work_card)
        )
 
        adapter = ConversationAdapter(categories) { category ->
            val intent = android.content.Intent(this, ConversationTopicActivity::class.java).apply {
                putExtra("CATEGORY_NAME", category.name)
            }
            startActivity(intent)
        }

        rvConversation.adapter = adapter
    }
}