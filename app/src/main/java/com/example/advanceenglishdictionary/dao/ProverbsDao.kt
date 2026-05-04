package com.example.advanceenglishdictionary.dao

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.advanceenglishdictionary.database.PhrasesDatabaseHelper
import com.example.advanceenglishdictionary.models.Proverbs

class ProverbsDao(context: Context) {
    private val TABLE_NAME = "Proverbs"
    private val dbHelper = PhrasesDatabaseHelper(context)

    fun getAllProverbs(): List<Proverbs> {
        val phrases = mutableListOf<Proverbs>()
        val db: SQLiteDatabase = dbHelper.openDatabase()
        try {
            val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
            if (cursor.moveToFirst()) {
                do {
                    phrases.add(cursorToModel(cursor))
                } while (cursor.moveToNext())
            }
            cursor.close()
        } finally {
            db.close()
        }
        return phrases
    }

    private fun cursorToModel(cursor: Cursor): Proverbs {
        return Proverbs(
            id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
            title = cursor.getString(cursor.getColumnIndexOrThrow("title")),
            description = cursor.getString(cursor.getColumnIndexOrThrow("desc")),
            favorite = cursor.getInt(cursor.getColumnIndexOrThrow("favorite"))
        )
    }
}