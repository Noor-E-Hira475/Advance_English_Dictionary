package com.example.advanceenglishdictionary.dao

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.advanceenglishdictionary.database.PhrasesDatabaseHelper
import com.example.advanceenglishdictionary.models.CommonPhrase

class CommonPhrasesDao(private val context: Context) {

    private val dbHelper = PhrasesDatabaseHelper(context)

    /** Get all phrases */
    fun getAllPhrases(): List<CommonPhrase> {
        val phrases = mutableListOf<CommonPhrase>()
        val db: SQLiteDatabase = dbHelper.openDatabase()
        try {
            val cursor: Cursor = db.rawQuery("SELECT * FROM commonUsefulPhrases", null)
            if (cursor.moveToFirst()) {
                do {
                    phrases.add(cursorToPhrase(cursor))
                } while (cursor.moveToNext())
            }
            cursor.close()
        } finally {
            db.close()
        }
        return phrases
    }

    /** Get phrase by categoryId */
    fun getPhrasesByCategory(categoryId: Int): List<CommonPhrase> {
        val phrases = mutableListOf<CommonPhrase>()
        val db: SQLiteDatabase = dbHelper.openDatabase()
        try {
            val cursor: Cursor = db.rawQuery(
                "SELECT * FROM commonUsefulPhrases WHERE categoryId = ?",
                arrayOf(categoryId.toString())
            )
            if (cursor.moveToFirst()) {
                do {
                    phrases.add(cursorToPhrase(cursor))
                } while (cursor.moveToNext())
            }
            cursor.close()
        } finally {
            db.close()
        }
        return phrases
    }

    /** Helper function to convert cursor to CommonPhrase model */
    private fun cursorToPhrase(cursor: Cursor): CommonPhrase {
        return CommonPhrase(
            id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
            categoryId = cursor.getInt(cursor.getColumnIndexOrThrow("categoryId")),
            categories = cursor.getString(cursor.getColumnIndexOrThrow("categories"))
        )
    }
}