package com.example.advanceenglishdictionary.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.advanceenglishdictionary.adapters.CommonPhrasesAdapter
import com.example.advanceenglishdictionary.dao.CommonPhrasesDao
import com.example.advanceenglishdictionary.databinding.ActivityCommonPhrasesBinding

class CommonPhrasesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCommonPhrasesBinding
    private lateinit var phrasesDao: CommonPhrasesDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCommonPhrasesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        phrasesDao = CommonPhrasesDao(this)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val allPhrases = phrasesDao.getAllPhrases()

        // Pass lambda to handle click
        val adapter = CommonPhrasesAdapter(allPhrases) { selectedCategory ->
            // Navigate to detail activity
            val intent = Intent(this, CommonPhraseDetailActivity::class.java)
            intent.putExtra("cid", selectedCategory.id)
            startActivity(intent)
        }

        binding.recyclerViewPhrases.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewPhrases.adapter = adapter
    }
}