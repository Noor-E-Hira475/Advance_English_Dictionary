package com.example.advanceenglishdictionary.ui.state

sealed interface UiState<out T> {
    /** Nothing has happened yet — initial screen state or after a reset. */
    data object Idle : UiState<Nothing>
    data object Loading : UiState<Nothing>
    data class Success<out T>(val data: T) : UiState<T>
    data class Error(val message: String) : UiState<Nothing>
}
