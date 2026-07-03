package com.example.advanceenglishdictionary.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.advanceenglishdictionary.adapters.IdiomsCategoryAdapter
import com.example.advanceenglishdictionary.databinding.ActivityIdiomsBinding
import com.example.advanceenglishdictionary.fragments.IdiomsCategoryFragment

class IdiomsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIdiomsBinding

    private val categories = listOf(
        "basic",
        "intermediate",
        "advanced"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIdiomsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupCategoriesRecyclerView()

        // Load the default first category on creation
        if (savedInstanceState == null && categories.isNotEmpty()) {
            openFragment(categories[0])
        }
    }

    private fun setupCategoriesRecyclerView() {
        val adapter = IdiomsCategoryAdapter(categories) { selectedCategory ->
            openFragment(selectedCategory)
        }

        binding.idiomsCategoryRecyclerView.layoutManager =
            GridLayoutManager(this, 3)
        binding.idiomsCategoryRecyclerView.adapter = adapter
    }

    private fun openFragment(category: String) {
        val fragment = IdiomsCategoryFragment.newInstance(category)
        supportFragmentManager.beginTransaction()
            .replace(binding.idiomsFragmentContainer.id, fragment)
            .commit()
    }
}
