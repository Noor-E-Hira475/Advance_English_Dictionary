package com.example.advanceenglishdictionary.dao

import android.content.Context
import android.database.Cursor
import com.example.advanceenglishdictionary.database.DictionaryDatabaseHelper
import com.example.advanceenglishdictionary.models.WordDescription
import com.example.advanceenglishdictionary.models.WordKey

class DictionaryDao(context: Context) {

    private val dbHelper = DictionaryDatabaseHelper(context)

    fun getWordsByLetter(letter: String, limit: Int = 500): List<WordKey> {
        val list = mutableListOf<WordKey>()
        val db = dbHelper.openDatabase()

        try {
            val query = "SELECT _idref, word, wordasID FROM keys WHERE word LIKE ? ORDER BY word ASC LIMIT ?"
            val cursor = db.rawQuery(query, arrayOf("$letter%", limit.toString()))
            cursor.use {
                while (it.moveToNext()) {
                    list.add(it.toWordKey())
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.close()
        }

        return list
    }

    fun searchWords(query: String, limit: Int = 200): List<WordKey> {
        val list = mutableListOf<WordKey>()
        if (query.isBlank()) return list

        val db = dbHelper.openDatabase()

        try {
            val sql = "SELECT _idref, word, wordasID FROM keys WHERE word LIKE ? ORDER BY word ASC LIMIT ?"
            val cursor = db.rawQuery(sql, arrayOf("$query%", limit.toString()))
            cursor.use {
                while (it.moveToNext()) {
                    list.add(it.toWordKey())
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.close()
        }

        return list
    }

    fun getWordById(idRef: Int): WordKey? {
        val db = dbHelper.openDatabase()

        return try {
            val cursor = db.rawQuery("SELECT _idref, word, wordasID FROM keys WHERE _idref = ?", arrayOf(idRef.toString()))
            cursor.use {
                if (it.moveToFirst()) it.toWordKey() else null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        } finally {
            db.close()
        }
    }

    fun getWordDescriptions(idRef: Int): List<WordDescription> {
        val list = mutableListOf<WordDescription>()
        val db = dbHelper.openDatabase()

        try {
            val cursor = db.rawQuery("SELECT * FROM description WHERE _id = ?", arrayOf(idRef.toString()))
            cursor.use {
                while (it.moveToNext()) {
                    list.add(it.toWordDescription())
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.close()
        }

        return list
    }

    private fun Cursor.toWordKey(): WordKey {
        val idRefIndex = getColumnIndex("_idref")
        val wordIndex = getColumnIndex("word")
        val wordAsIdIndex = getColumnIndex("wordasID")

        return WordKey(
            idRef = if (idRefIndex != -1) getInt(idRefIndex) else 0,
            word = if (wordIndex != -1) getString(wordIndex) else "",
            wordasID = if (wordAsIdIndex != -1) getString(wordAsIdIndex) else null
        )
    }

    private fun Cursor.toWordDescription(): WordDescription {
        fun getStringOrNull(colName: String): String? {
            val idx = getColumnIndex(colName)
            return if (idx != -1 && !isNull(idx)) getString(idx) else null
        }

        val idIdx = getColumnIndex("_id")
        val idVal = if (idIdx != -1) getInt(idIdx) else 0

        return WordDescription(
            id = idVal,
            definition = getStringOrNull("definition"),
            category = getStringOrNull("category"),
            synonyms = getStringOrNull("synonyms"),
            hyponyms = getStringOrNull("hyponyms"),
            instanceHyponyms = getStringOrNull("instanceHyponyms"),
            hypernyms = getStringOrNull("hypernyms"),
            instanceHypernyms = getStringOrNull("instanceHypernyms"),
            partHolonyms = getStringOrNull("partHolonyms"),
            memberHolonyms = getStringOrNull("memberHolonyms"),
            substanceHolonyms = getStringOrNull("substanceHolonyms"),
            partMeronyms = getStringOrNull("partMeronyms"),
            memberMeronyms = getStringOrNull("memberMeronyms"),
            substanceMeronyms = getStringOrNull("substanceMeronyms"),
            examples = getStringOrNull("examples"),
            antonyms = getStringOrNull("antonyms"),
            similar = getStringOrNull("similar"),
            also = getStringOrNull("also")
        )
    }
}
