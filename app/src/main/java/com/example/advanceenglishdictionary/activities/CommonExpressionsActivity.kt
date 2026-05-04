package com.example.advanceenglishdictionary.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.advanceenglishdictionary.adapters.CommonExpressionsAdapter
import com.example.advanceenglishdictionary.dao.CommonExpressionsDao
import com.example.advanceenglishdictionary.databinding.ActivityCommonExpressionsBinding

class CommonExpressionsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCommonExpressionsBinding
    private lateinit var expressionsDao: CommonExpressionsDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCommonExpressionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        expressionsDao = CommonExpressionsDao(this)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val allExpressions = expressionsDao.getAllCategories()
        // Pass lambda to handle click
        val adapter = CommonExpressionsAdapter(allExpressions) { selectedCategory ->
            // Navigate to detail activity
            val intent = Intent(this, CommonExpressionsDetailActivity::class.java)
            intent.putExtra("cid", selectedCategory.id)
            startActivity(intent)
        }
        binding.ExpressionRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.ExpressionRecyclerView.adapter = adapter
    }
}