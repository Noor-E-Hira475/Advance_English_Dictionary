package com.example.advanceenglishdictionary.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.advanceenglishdictionary.adapters.CommonPhraseDetailAdapter
import com.example.advanceenglishdictionary.dao.CommonPhraseDetailDao
import com.example.advanceenglishdictionary.databinding.ActivityCommonPhraseDetailBinding

class CommonPhraseDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCommonPhraseDetailBinding
    private lateinit var detailsDao: CommonPhraseDetailDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ViewBinding setup
        binding = ActivityCommonPhraseDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //DAO initialization
        detailsDao = CommonPhraseDetailDao(this)

        // Receive category id (cid) from Intent
        val cid = intent.getIntExtra("cid", -1)

        if (cid == -1) {
            // Invalid category, close screen
            finish()
            return
        }

        //  Load phrases related to this category
        loadPhrases(cid)
    }

    private fun loadPhrases(cid: Int) {

        // DB call
        val phraseList = detailsDao.getPhrasesByCategoryId(cid)

        // RecyclerView setup
        binding.recyclerViewPhrasesDetail.layoutManager =
            LinearLayoutManager(this)

        binding.recyclerViewPhrasesDetail.adapter =
            CommonPhraseDetailAdapter(phraseList)
    }
}