package com.example.advanceenglishdictionary.dao

import android.content.Context
import com.example.advanceenglishdictionary.models.Quote
import org.json.JSONObject

class QuotesDao(private val context: Context) {

    private fun getQuotesObject(): JSONObject? {
        return try {
            val jsonString = context.assets.open("quotes.json")
                .bufferedReader()
                .use { it.readText() }
            JSONObject(jsonString).optJSONObject("quotes")
        } catch (e: Exception) {
            null
        }
    }

    fun getCategories(): List<String> {
        val quotesObject = getQuotesObject() ?: return emptyList()
        return quotesObject.keys().asSequence().toList()
    }

    fun getQuotesByCategory(category: String): List<Quote> {
        val quotesObject = getQuotesObject() ?: return emptyList()
        val array = quotesObject.optJSONArray(category) ?: return emptyList()

        return (0 until array.length()).map { i ->
            val item = array.getJSONObject(i)
            Quote(
                quote = item.optString("quote", ""),
                author = item.optString("author", "")
            )
        }
    }
}