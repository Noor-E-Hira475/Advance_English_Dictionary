package com.example.advanceenglishdictionary.repository

import com.example.advanceenglishdictionary.dao.QuotesDao
import com.example.advanceenglishdictionary.models.Quote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class QuotesRepository(private val quotesDao: QuotesDao) {

    suspend fun getCategories(): List<String> = withContext(Dispatchers.IO) {
        quotesDao.getCategories()
    }

    suspend fun getQuotesByCategory(category: String): List<Quote> = withContext(Dispatchers.IO) {
        quotesDao.getQuotesByCategory(category)
    }
}
