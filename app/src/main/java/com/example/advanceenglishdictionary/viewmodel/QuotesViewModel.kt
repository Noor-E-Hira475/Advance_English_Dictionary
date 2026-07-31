package com.example.advanceenglishdictionary.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.advanceenglishdictionary.models.Quote
import com.example.advanceenglishdictionary.repository.QuotesRepository
import com.example.advanceenglishdictionary.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class QuotesViewModel(private val repository: QuotesRepository) : ViewModel() {

    private val _categoriesState = MutableStateFlow<UiState<List<String>>>(UiState.Loading)
    val categoriesState: StateFlow<UiState<List<String>>> = _categoriesState.asStateFlow()

    private val _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory: StateFlow<String?> = _selectedCategory.asStateFlow()

    private val _uiState = MutableStateFlow<UiState<List<Quote>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Quote>>> = _uiState.asStateFlow()

    init {
        loadCategories()
    }

    fun loadCategories() {
        viewModelScope.launch {
            _categoriesState.value = UiState.Loading
            try {
                val categories = repository.getCategories()
                if (categories.isEmpty()) {
                    _categoriesState.value = UiState.Error("No categories found")
                } else {
                    _categoriesState.value = UiState.Success(categories)
                    if (_selectedCategory.value == null) {
                        selectCategory(categories.first())
                    }
                }
            } catch (e: Exception) {
                _categoriesState.value = UiState.Error(e.localizedMessage ?: "Failed to load categories")
            }
        }
    }

    fun selectCategory(category: String) {
        if (_selectedCategory.value == category && _uiState.value is UiState.Success) return
        _selectedCategory.value = category
        loadQuotesByCategory(category)
    }

    fun loadQuotesByCategory(category: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val quotes = repository.getQuotesByCategory(category)
                _uiState.value = UiState.Success(quotes)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.localizedMessage ?: "Failed to load quotes")
            }
        }
    }
}
