package com.example.advanceenglishdictionary.repository

import com.example.advanceenglishdictionary.dao.ConfusedWordsDao
import com.example.advanceenglishdictionary.models.ConfusedWord
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ConfusedWordsRepository(private val confusedWordsDao: ConfusedWordsDao) {

    suspend fun getConfusedWords(): List<ConfusedWord> = withContext(Dispatchers.IO) {
        confusedWordsDao.loadConfusedWords()
    }
}
