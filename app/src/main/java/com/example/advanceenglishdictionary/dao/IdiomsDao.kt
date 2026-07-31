package com.example.advanceenglishdictionary.dao

import android.content.Context
import com.example.advanceenglishdictionary.models.Idioms
import org.json.JSONObject

class IdiomsDao(private val context: Context) {

    private fun getRootObject(): JSONObject? {
        return try {
            val jsonString = context.assets.open("idioms.json")
                .bufferedReader()
                .use { it.readText() }
            JSONObject(jsonString)
        } catch (e: Exception) {
            null
        }
    }

    fun getCategories(): List<String> {
        val root = getRootObject() ?: return emptyList()
        return root.keys().asSequence().toList()
    }

    fun getIdiomsByCategory(category: String): List<Idioms> {
        val root = getRootObject() ?: return emptyList()
        val array = root.optJSONArray(category) ?: return emptyList()

        return (0 until array.length()).map { i ->
            val item = array.getJSONObject(i)
            Idioms(
                idiom = item.optString("idiom", ""),
                meaning = item.optString("meaning", ""),
                example = item.optString("example", "")
            )
        }
    }
}
