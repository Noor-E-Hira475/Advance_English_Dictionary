package com.example.advanceenglishdictionary.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.advanceenglishdictionary.repository.DictionaryApiRepository

// ─────────────────────────────────────────────────────────────────────────────
//  DictionaryApiViewModelFactory
//
//  Android's ViewModelProvider cannot call constructors with arguments by
//  default. This factory tells it HOW to create DictionaryApiViewModel by
//  passing the repository in.
//
//  HOW TO USE (in an Activity):
//
//    private val viewModel: DictionaryApiViewModel by viewModels {
//        DictionaryApiViewModelFactory(DictionaryApiRepository())
//    }
//
//  Or manually (without the `by viewModels` delegate):
//
//    val factory = DictionaryApiViewModelFactory(DictionaryApiRepository())
//    val viewModel = ViewModelProvider(this, factory)[DictionaryApiViewModel::class.java]
// ─────────────────────────────────────────────────────────────────────────────

class DictionaryApiViewModelFactory(
    private val repository: DictionaryApiRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DictionaryApiViewModel::class.java)) {
            return DictionaryApiViewModel(repository) as T
        }
        throw IllegalArgumentException(
            "Unknown ViewModel class: ${modelClass.name}. " +
            "Expected DictionaryApiViewModel."
        )
    }
}
