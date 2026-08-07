package com.example.advanceenglishdictionary.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.advanceenglishdictionary.adapters.ConfusedWordsAdapter
import com.example.advanceenglishdictionary.dao.ConfusedWordsDao
import com.example.advanceenglishdictionary.databinding.ActivityConfusedWordsBinding
import com.example.advanceenglishdictionary.extensions.collectState
import com.example.advanceenglishdictionary.extensions.showToast
import com.example.advanceenglishdictionary.repository.ConfusedWordsRepository
import com.example.advanceenglishdictionary.ui.state.UiState
import com.example.advanceenglishdictionary.viewmodel.ConfusedWordsViewModel
import com.example.advanceenglishdictionary.viewmodel.ConfusedWordsViewModelFactory

class ConfusedWordsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConfusedWordsBinding
    private lateinit var adapter: ConfusedWordsAdapter

    private val viewModel: ConfusedWordsViewModel by viewModels {
        ConfusedWordsViewModelFactory(
            ConfusedWordsRepository(ConfusedWordsDao(this))
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfusedWordsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        adapter = ConfusedWordsAdapter { _, index ->
            val intent = Intent(this, ConfusedWordsDetailActivity::class.java).apply {
                putExtra("WORD_INDEX", index)
            }
            startActivity(intent)
        }

        binding.recyclerViewConfusedWords.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewConfusedWords.adapter = adapter
    }

    private fun observeViewModel() {
        collectState(viewModel.uiState) { state ->
            when (state) {
                is UiState.Loading -> {
                    // Loading state
                }
                is UiState.Success -> {
                    adapter.updateData(state.data)
                }
                is UiState.Error -> {
                    showToast(state.message)
                }
            }
        }
    }
}
