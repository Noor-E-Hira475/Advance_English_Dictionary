package com.example.advanceenglishdictionary.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.advanceenglishdictionary.adapters.QuotesCategoryAdapter
import com.example.advanceenglishdictionary.dao.QuotesDao
import com.example.advanceenglishdictionary.databinding.ActivityQuotesBinding
import com.example.advanceenglishdictionary.fragments.QuotesCategoryFragment
import com.example.advanceenglishdictionary.repository.QuotesRepository
import com.example.advanceenglishdictionary.ui.state.UiState
import com.example.advanceenglishdictionary.viewmodel.QuotesViewModel
import com.example.advanceenglishdictionary.viewmodel.QuotesViewModelFactory
import kotlinx.coroutines.launch

class QuotesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuotesBinding
    private var categoryAdapter: QuotesCategoryAdapter? = null

    private val viewModel: QuotesViewModel by viewModels {
        QuotesViewModelFactory(QuotesRepository(QuotesDao(applicationContext)))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.quotesCategoryRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        observeViewModel()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.categoriesState.collect { state ->
                        if (state is UiState.Success) {
                            setupCategoriesAdapter(state.data)
                        }
                    }
                }
                launch {
                    viewModel.selectedCategory.collect { category ->
                        category?.let {
                            categoryAdapter?.setSelectedCategory(it)
                            openFragment(it)
                        }
                    }
                }
            }
        }
    }

    private fun setupCategoriesAdapter(categories: List<String>) {
        if (categoryAdapter == null) {
            categoryAdapter = QuotesCategoryAdapter(categories) { selectedCategory ->
                viewModel.selectCategory(selectedCategory)
            }
            binding.quotesCategoryRecyclerView.adapter = categoryAdapter
        }
    }

    private fun openFragment(category: String) {
        val currentFragment = supportFragmentManager.findFragmentById(binding.quotesFragmentContainer.id)
        if (currentFragment is QuotesCategoryFragment && currentFragment.getCategory() == category) {
            return
        }

        val fragment = QuotesCategoryFragment.newInstance(category)
        supportFragmentManager.beginTransaction()
            .replace(binding.quotesFragmentContainer.id, fragment)
            .commit()
    }
}
