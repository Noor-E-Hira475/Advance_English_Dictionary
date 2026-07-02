package com.example.advanceenglishdictionary.dao

import android.content.Context
import com.example.advanceenglishdictionary.models.Quote
import org.json.JSONObject

class QuotesDao(private val context: Context) {

    fun getQuotesByCategory(category: String): List<Quote> {
        val list = mutableListOf<Quote>()
        try {
            val jsonString = context.assets.open("quotes.json")
                .bufferedReader()
                .use { it.readText() }

            val root = JSONObject(jsonString)
            val quotesObject = root.getJSONObject("quotes")

            if (quotesObject.has(category)) {
                val quotesArray = quotesObject.getJSONArray(category)
                for (i in 0 until quotesArray.length()) {
                    val item = quotesArray.getJSONObject(i)
                    list.add(
                        Quote(
                            quote = item.getString("quote"),
                            author = item.getString("author")
                        )
                    )
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return list
    }
}