package com.example.advanceenglishdictionary.dao

import android.content.Context
import com.example.advanceenglishdictionary.models.Idioms
import org.json.JSONObject

class IdiomsDao(private val context: Context) {

    fun getIdiomsByCategory(category: String): List<Idioms> {
        val list = mutableListOf<Idioms>()
        try {
            val jsonString = context.assets.open("idioms.json")
                .bufferedReader()
                .use { it.readText() }

            val root = JSONObject(jsonString)
            if (root.has(category)) {
                val array = root.getJSONArray(category)
                for (i in 0 until array.length()) {
                    val item = array.getJSONObject(i)
                    list.add(
                        Idioms(
                            idiom = item.optString("idiom", ""),
                            meaning = item.optString("meaning", ""),
                            example = item.optString("example", "")
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
