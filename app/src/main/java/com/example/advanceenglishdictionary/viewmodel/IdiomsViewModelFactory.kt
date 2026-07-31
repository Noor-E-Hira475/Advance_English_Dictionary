package com.example.advanceenglishdictionary.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.advanceenglishdictionary.repository.IdiomsRepository

class IdiomsViewModelFactory(
    private val repository: IdiomsRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(IdiomsViewModel::class.java)) {
            return IdiomsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
