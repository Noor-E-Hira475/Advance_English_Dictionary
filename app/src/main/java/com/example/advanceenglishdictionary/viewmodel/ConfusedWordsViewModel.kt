package com.example.advanceenglishdictionary.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.advanceenglishdictionary.models.ConfusedWord
import com.example.advanceenglishdictionary.repository.ConfusedWordsRepository
import com.example.advanceenglishdictionary.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ConfusedWordsViewModel(private val repository: ConfusedWordsRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<ConfusedWord>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<ConfusedWord>>> = _uiState.asStateFlow()

    private val _detailState = MutableStateFlow<UiState<ConfusedWord>>(UiState.Loading)
    val detailState: StateFlow<UiState<ConfusedWord>> = _detailState.asStateFlow()

    init {
        loadConfusedWords()
    }

    fun loadConfusedWords() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val words = repository.getConfusedWords()
                if (words.isEmpty()) {
                    _uiState.value = UiState.Error("No confused words found")
                } else {
                    _uiState.value = UiState.Success(words)
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.localizedMessage ?: "Failed to load confused words")
            }
        }
    }

    fun loadWordDetail(index: Int) {
        viewModelScope.launch {
            _detailState.value = UiState.Loading
            try {
                val words = repository.getConfusedWords()
                val word = words.getOrNull(index)
                if (word != null) {
                    _detailState.value = UiState.Success(word)
                } else {
                    _detailState.value = UiState.Error("Confused word not found")
                }
            } catch (e: Exception) {
                _detailState.value = UiState.Error(e.localizedMessage ?: "Failed to load word detail")
            }
        }
    }
}
