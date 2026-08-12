package com.example.advanceenglishdictionary.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.advanceenglishdictionary.dao.DictionaryDao
import com.example.advanceenglishdictionary.models.WordDescription
import com.example.advanceenglishdictionary.models.WordKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WordDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = DictionaryDao(application)

    private val _wordKey = MutableLiveData<WordKey?>()
    val wordKey: LiveData<WordKey?> get() = _wordKey

    private val _descriptions = MutableLiveData<List<WordDescription>>()
    val descriptions: LiveData<List<WordDescription>> get() = _descriptions

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun loadWordDetail(idRef: Int) {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val key = dao.getWordById(idRef)
            val descList = dao.getWordDescriptions(idRef)

            withContext(Dispatchers.Main) {
                _wordKey.value = key
                _descriptions.value = descList
                _isLoading.value = false
            }
        }
    }
}
