package com.example.advanceenglishdictionary.dao

import android.content.Context
import com.example.advanceenglishdictionary.models.ConfusedWord
import org.json.JSONObject

class ConfusedWordsDao(private val context: Context) {

    fun loadConfusedWords(): List<ConfusedWord> {
        val list = mutableListOf<ConfusedWord>()
        try {
            val jsonString = context.assets.open("confused_words.json")
                .bufferedReader()
                .use { it.readText() }

            val root = JSONObject(jsonString)
            val wordsArray = root.getJSONArray("words")

            for (i in 0 until wordsArray.length()) {
                val item = wordsArray.getJSONObject(i)

                val pairArray = item.getJSONArray("pair")
                val pair = (0 until pairArray.length()).map { pairArray.getString(it) }

                val defArray = item.getJSONArray("definitions")
                val definitions = (0 until defArray.length()).map { defArray.getString(it) }

                val example = item.getString("example")

                list.add(ConfusedWord(pair, definitions, example))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return list
    }
}
