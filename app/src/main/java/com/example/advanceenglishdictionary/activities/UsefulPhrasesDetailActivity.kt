package com.example.advanceenglishdictionary.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.advanceenglishdictionary.adapters.UsefulPhraseAdapter
import com.example.advanceenglishdictionary.dao.UsefulPhrasesDao
import com.example.advanceenglishdictionary.databinding.ActivityUsefulPhrasesDetailBinding

class UsefulPhrasesDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUsefulPhrasesDetailBinding
    private lateinit var phrasesDao: UsefulPhrasesDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUsefulPhrasesDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        phrasesDao = UsefulPhrasesDao(this)

        val categoryId = intent.getIntExtra("CATEGORY_ID", -1)
        val sourceLangCol = intent.getStringExtra("SOURCE_LANG_COL") ?: "english"
        val targetLangCol = intent.getStringExtra("TARGET_LANG_COL") ?: "arabic"

        if (categoryId == -1) {
            finish()
            return
        }

        loadPhrases(categoryId, sourceLangCol, targetLangCol)
    }

    private fun loadPhrases(categoryId: Int, sourceLangCol: String, targetLangCol: String) {
        val phrases = phrasesDao.getPhrasesByCategory(categoryId, sourceLangCol, targetLangCol)

        binding.rvPhraseDetails.layoutManager = LinearLayoutManager(this)
        binding.rvPhraseDetails.adapter = UsefulPhraseAdapter(phrases)
    }
}
