package com.example.advanceenglishdictionary.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.advanceenglishdictionary.adapters.QuotesCategoryAdapter
import com.example.advanceenglishdictionary.databinding.ActivityQuotesBinding
import com.example.advanceenglishdictionary.fragments.QuotesCategoryFragment

class QuotesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuotesBinding

    private val categories = listOf(
        "inspiration",
        "love",
        "happiness",
        "success",
        "wisdom",
        "friendship",
        "life",
        "motivation",
        "courage",
        "change"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupCategoriesRecyclerView()

        // Load the default first category on creation
        if (savedInstanceState == null && categories.isNotEmpty()) {
            openFragment(categories[0])
        }
    }

    private fun setupCategoriesRecyclerView() {
        val adapter = QuotesCategoryAdapter(categories) { selectedCategory ->
            openFragment(selectedCategory)
        }

        binding.quotesCategoryRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.quotesCategoryRecyclerView.adapter = adapter
    }

    private fun openFragment(category: String) {
        val fragment = QuotesCategoryFragment.newInstance(category)
        supportFragmentManager.beginTransaction()
            .replace(binding.quotesFragmentContainer.id, fragment)
            .commit()
    }
}
