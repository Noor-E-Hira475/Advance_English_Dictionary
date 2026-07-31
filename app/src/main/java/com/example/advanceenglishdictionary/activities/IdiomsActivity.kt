package com.example.advanceenglishdictionary.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.advanceenglishdictionary.adapters.IdiomsCategoryAdapter
import com.example.advanceenglishdictionary.dao.IdiomsDao
import com.example.advanceenglishdictionary.databinding.ActivityIdiomsBinding
import com.example.advanceenglishdictionary.fragments.IdiomsCategoryFragment
import com.example.advanceenglishdictionary.repository.IdiomsRepository
import com.example.advanceenglishdictionary.ui.state.UiState
import com.example.advanceenglishdictionary.viewmodel.IdiomsViewModel
import com.example.advanceenglishdictionary.viewmodel.IdiomsViewModelFactory
import kotlinx.coroutines.launch

class IdiomsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIdiomsBinding
    private var categoryAdapter: IdiomsCategoryAdapter? = null

    private val viewModel: IdiomsViewModel by viewModels {
        IdiomsViewModelFactory(IdiomsRepository(IdiomsDao(applicationContext)))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIdiomsBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
            binding.idiomsCategoryRecyclerView.layoutManager = GridLayoutManager(this, 3)
            categoryAdapter = IdiomsCategoryAdapter(categories) { selectedCategory ->
                viewModel.selectCategory(selectedCategory)
            }
            binding.idiomsCategoryRecyclerView.adapter = categoryAdapter
        }
    }

    private fun openFragment(category: String) {
        val currentFragment = supportFragmentManager.findFragmentById(binding.idiomsFragmentContainer.id)
        if (currentFragment is IdiomsCategoryFragment && currentFragment.getCategory() == category) {
            return
        }

        val fragment = IdiomsCategoryFragment.newInstance(category)
        supportFragmentManager.beginTransaction()
            .replace(binding.idiomsFragmentContainer.id, fragment)
            .commit()
    }
}
