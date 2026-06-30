package com.example.advanceenglishdictionary.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.advanceenglishdictionary.adapters.ConfusedWordsAdapter
import com.example.advanceenglishdictionary.dao.ConfusedWordsDao
import com.example.advanceenglishdictionary.databinding.ActivityConfusedWordsBinding

class ConfusedWordsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConfusedWordsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfusedWordsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val dao = ConfusedWordsDao(this)
        val wordList = dao.loadConfusedWords()

        val adapter = ConfusedWordsAdapter(wordList) { _, index ->
            val intent = Intent(this, ConfusedWordsDetailActivity::class.java)
            intent.putExtra("WORD_INDEX", index)
            startActivity(intent)
        }

        binding.recyclerViewConfusedWords.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewConfusedWords.adapter = adapter
    }
}
