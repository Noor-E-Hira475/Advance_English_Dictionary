package com.example.advanceenglishdictionary.dao

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.advanceenglishdictionary.database.PhrasesDatabaseHelper
import com.example.advanceenglishdictionary.models.DaySentence

class SentenceOfDayDao(context: Context) {

    private val TABLE_NAME = "PhraseOfDay"

    private val dbHelper = PhrasesDatabaseHelper(context)

    fun getAllSentences() : List<DaySentence> {
        val sentences = mutableListOf<DaySentence>()
        val db: SQLiteDatabase = dbHelper.openDatabase()

        try {
            val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_NAME",null)
            if(cursor.moveToFirst()){
                do {
                    sentences.add(cursorToModel(cursor))
                } while(cursor.moveToNext())
            }
            cursor.close()
        } finally {
            db.close()
        }
        return sentences
    }

    private fun cursorToModel(cursor: Cursor) : DaySentence{
        return DaySentence(
            id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
            sentence = cursor.getString(cursor.getColumnIndexOrThrow("phrase")),
            meaning = cursor.getString(cursor.getColumnIndexOrThrow("meaning")),
            example = cursor.getString(cursor.getColumnIndexOrThrow("example"))
        )
    }
}