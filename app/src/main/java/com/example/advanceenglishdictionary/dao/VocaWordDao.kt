package com.example.advanceenglishdictionary.dao

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.advanceenglishdictionary.database.PhrasesDatabaseHelper
import com.example.advanceenglishdictionary.models.VocaWord

class VocaWordDao(context: Context) {

    private val TABLE_NAME = "Voca5000Words"

    private val dbHelper = PhrasesDatabaseHelper(context)

    fun getAllVocaWords() : List<VocaWord> {
        val phrases = mutableListOf<VocaWord>()
        val db: SQLiteDatabase = dbHelper.openDatabase()
        try {
            val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
            if(cursor.moveToFirst()){
                do {
                     phrases.add(cursorToModel(cursor))
                } while(cursor.moveToNext())
            }
            cursor.close()
        } finally {
            db.close()
        }
        return phrases
    }

    private fun cursorToModel(cursor: Cursor) : VocaWord {
        return VocaWord(
            id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
            keyword = cursor.getString(cursor.getColumnIndexOrThrow("keyword")),
            type = cursor.getString(cursor.getColumnIndexOrThrow("type")),
            definition = cursor.getString(cursor.getColumnIndexOrThrow("definition")),
            example = cursor.getString(cursor.getColumnIndexOrThrow("example")),
            favorite = cursor.getInt(cursor.getColumnIndexOrThrow("favorite"))
        )
    }
}