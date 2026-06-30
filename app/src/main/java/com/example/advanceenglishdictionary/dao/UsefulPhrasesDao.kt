package com.example.advanceenglishdictionary.dao

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.advanceenglishdictionary.database.PhrasesDatabaseHelper
import com.example.advanceenglishdictionary.models.UsefulPhrase

class UsefulPhrasesDao(context: Context) {

    private val dbHelper = PhrasesDatabaseHelper(context)

    /** Get all phrases for a specific category, using dynamic columns for source and target languages */
    fun getPhrasesByCategory(
        cId: Int,
        sourceLanguageCol: String = "english",
        targetLanguageCol: String = "arabic"
    ): List<UsefulPhrase> {
        val list = mutableListOf<UsefulPhrase>()
        val db: SQLiteDatabase = dbHelper.openDatabase()

        try {
            // Escape/sanitize the column names or pull them safely since we populate them from a trusted list
            val cursor: Cursor = db.rawQuery(
                "SELECT _id, c_id, $sourceLanguageCol, $targetLanguageCol FROM phrases_details WHERE c_id = ?",
                arrayOf(cId.toString())
            )

            if (cursor.moveToFirst()) {
                do {
                    list.add(
                        UsefulPhrase(
                            id = cursor.getInt(cursor.getColumnIndexOrThrow("_id")),
                            cId = cursor.getInt(cursor.getColumnIndexOrThrow("c_id")),
                            source = cursor.getString(cursor.getColumnIndexOrThrow(sourceLanguageCol)),
                            translation = cursor.getString(cursor.getColumnIndexOrThrow(targetLanguageCol))
                        )
                    )
                } while (cursor.moveToNext())
            }
            cursor.close()
        } finally {
            db.close()
        }
        return list
    }
}
