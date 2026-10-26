package com.example.advanceenglishdictionary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.advanceenglishdictionary.models.api.DictionaryResponse
import com.example.advanceenglishdictionary.repository.DictionaryApiRepository
import com.example.advanceenglishdictionary.ui.state.UiState
import kotlinx.coroutines.launch

// ─────────────────────────────────────────────────────────────────────────────
//  DictionaryApiViewModel
//
//  Bridges DictionaryApiRepository (data layer) and the UI (Activity/Fragment).
//  Exposes a single LiveData<UiState<…>> that the UI observes.
//
//  HOW TO USE (from an Activity):
//    private val viewModel: DictionaryApiViewModel by viewModels {
//        DictionaryApiViewModelFactory(DictionaryApiRepository())
//    }
//
//    viewModel.wordState.observe(this) { state ->
//        when (state) {
//            is UiState.Loading -> showLoading()
//            is UiState.Success -> showDefinitions(state.data)
//            is UiState.Error   -> showError(state.message)
//        }
//    }
//
//    viewModel.searchWord("hello")
// ─────────────────────────────────────────────────────────────────────────────

class DictionaryApiViewModel(
    private val repository: DictionaryApiRepository
) : ViewModel() {

    // ── State ────────────────────────────────────────────────────────────────

    private val _wordState = MutableLiveData<UiState<List<DictionaryResponse>>>(UiState.Idle)

    /**
     * The UI observes this LiveData.
     * It cycles through: Idle → Loading → Success(data) | Error(message)
     */
    val wordState: LiveData<UiState<List<DictionaryResponse>>> get() = _wordState

    // Tracks the last successfully searched word so we can avoid redundant calls
    private var lastSearchedWord: String = ""

    // ── Public API ───────────────────────────────────────────────────────────

    /**
     * Looks up [word] via the online dictionary API.
     * Skips the call if [word] is blank or already the last searched term.
     *
     * @param word  The word to search (leading/trailing whitespace is trimmed).
     */
    fun searchWord(word: String) {
        val trimmed = word.trim()

        if (trimmed.isEmpty()) return
        if (trimmed.equals(lastSearchedWord, ignoreCase = true)) return  // no duplicate call

        lastSearchedWord = trimmed
        _wordState.value = UiState.Loading   // emit Loading immediately (on main thread)

        viewModelScope.launch {
            // Repository call is a suspend fun — runs on Dispatchers.IO inside the repo
            val result = repository.lookUpWord(trimmed)
            _wordState.value = result        // post result back to main thread via LiveData
        }
    }

    /**
     * Clears the current state and resets the last searched word back to Idle.
     * Call this when the search field is cleared by the user.
     */
    fun clearState() {
        lastSearchedWord = ""
        _wordState.value = UiState.Idle
    }
}
