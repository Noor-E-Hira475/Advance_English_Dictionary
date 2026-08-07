package com.example.advanceenglishdictionary.activities

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.advanceenglishdictionary.dao.ConfusedWordsDao
import com.example.advanceenglishdictionary.databinding.ActivityConfusedWordsDetailBinding
import com.example.advanceenglishdictionary.extensions.collectState
import com.example.advanceenglishdictionary.extensions.setBoldDefinition
import com.example.advanceenglishdictionary.extensions.showToast
import com.example.advanceenglishdictionary.models.ConfusedWord
import com.example.advanceenglishdictionary.repository.ConfusedWordsRepository
import com.example.advanceenglishdictionary.ui.state.UiState
import com.example.advanceenglishdictionary.viewmodel.ConfusedWordsViewModel
import com.example.advanceenglishdictionary.viewmodel.ConfusedWordsViewModelFactory

class ConfusedWordsDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConfusedWordsDetailBinding

    private val viewModel: ConfusedWordsViewModel by viewModels {
        ConfusedWordsViewModelFactory(
            ConfusedWordsRepository(ConfusedWordsDao(this))
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfusedWordsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val index = intent.getIntExtra("WORD_INDEX", -1)
        if (index == -1) {
            finish()
            return
        }

        observeViewModel()
        viewModel.loadWordDetail(index)
    }

    private fun observeViewModel() {
        collectState(viewModel.detailState) { state ->
            when (state) {
                is UiState.Loading -> {
                    // Loading state
                }
                is UiState.Success -> {
                    displayWordDetails(state.data)
                }
                is UiState.Error -> {
                    showToast(state.message)
                    finish()
                }
            }
        }
    }

    private fun displayWordDetails(word: ConfusedWord) {
        // Display pair title (e.g. "Accept / Except")
        binding.tvPairTitle.text = word.pair.joinToString(" / ")

        // Display definitions using extension to bold the confused word / first word
        val defViews = listOf(binding.tvDefinition1, binding.tvDefinition2, binding.tvDefinition3)
        for (i in defViews.indices) {
            val tv = defViews[i]
            val pairWord = word.pair.getOrNull(i)
            val definition = word.definitions.getOrNull(i)

            if (definition != null) {
                tv.visibility = View.VISIBLE
                tv.setBoldDefinition(definition, pairWord)
            } else {
                tv.visibility = View.GONE
            }
        }

        // Display example sentence
        binding.tvExample.text = word.example
    }
}
