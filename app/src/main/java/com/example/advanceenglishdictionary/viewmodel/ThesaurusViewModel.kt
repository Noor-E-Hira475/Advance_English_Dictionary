package com.example.advanceenglishdictionary.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.advanceenglishdictionary.dao.DictionaryDao
import com.example.advanceenglishdictionary.models.WordKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ThesaurusViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = DictionaryDao(application)

    private val _wordsList = MutableLiveData<List<WordKey>>()
    val wordsList: LiveData<List<WordKey>> get() = _wordsList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _selectedLetter = MutableLiveData<String>("A")
    val selectedLetter: LiveData<String> get() = _selectedLetter

    private var currentQuery: String = ""

    init {
        loadWordsForLetter("A")
    }

    fun selectLetter(letter: String) {
        _selectedLetter.value = letter.uppercase()
        currentQuery = ""
        loadWordsForLetter(letter)
    }

    fun setSearchQuery(query: String) {
        currentQuery = query.trim()
        if (currentQuery.isEmpty()) {
            val letter = _selectedLetter.value ?: "A"
            loadWordsForLetter(letter)
        } else {
            searchWords(currentQuery)
        }
    }

    private fun loadWordsForLetter(letter: String) {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val list = dao.getWordsByLetter(letter)
            withContext(Dispatchers.Main) {
                _wordsList.value = list
                _isLoading.value = false
            }
        }
    }

    private fun searchWords(query: String) {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val list = dao.searchWords(query)
            withContext(Dispatchers.Main) {
                _wordsList.value = list
                _isLoading.value = false
            }
        }
    }
}
