package com.example.advanceenglishdictionary.repository

import com.example.advanceenglishdictionary.dao.IdiomsDao
import com.example.advanceenglishdictionary.models.Idioms
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class IdiomsRepository(private val idiomsDao: IdiomsDao) {

    suspend fun getCategories(): List<String> = withContext(Dispatchers.IO) {
        idiomsDao.getCategories()
    }

    suspend fun getIdiomsByCategory(category: String): List<Idioms> = withContext(Dispatchers.IO) {
        idiomsDao.getIdiomsByCategory(category)
    }
}
