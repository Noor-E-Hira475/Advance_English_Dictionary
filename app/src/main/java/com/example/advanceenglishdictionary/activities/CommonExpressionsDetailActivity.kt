package com.example.advanceenglishdictionary.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.advanceenglishdictionary.adapters.CommonExpressionsDetailAdapter
import com.example.advanceenglishdictionary.dao.CommonExpressionsDetailDao
import com.example.advanceenglishdictionary.databinding.ActivityCommonExpressionsDetailBinding

class CommonExpressionsDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCommonExpressionsDetailBinding
    private lateinit var dao: CommonExpressionsDetailDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ViewBinding
        binding = ActivityCommonExpressionsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dao = CommonExpressionsDetailDao(this)

        // TEMP: hardcoded cid (launcher test)
        val cid = intent.getIntExtra("cid", -1)

        if (cid == -1) {
            // Invalid category, close screen
            finish()
            return
        }
        setupRecyclerView(cid)
    }

    private fun setupRecyclerView(cid: Int) {
        val list = dao.getDetailsByCid(cid)

        binding.ExpressionDetailRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.ExpressionDetailRecyclerView.adapter = CommonExpressionsDetailAdapter(list)
    }
}