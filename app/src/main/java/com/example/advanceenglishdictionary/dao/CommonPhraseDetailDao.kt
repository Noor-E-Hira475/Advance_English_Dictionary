package com.example.advanceenglishdictionary.dao

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.advanceenglishdictionary.database.PhrasesDatabaseHelper
import com.example.advanceenglishdictionary.models.CommonPhraseDetail

class CommonPhraseDetailDao(private val context: Context) {

    private val dbHelper = PhrasesDatabaseHelper(context)

    /** Get phrases by category id (cid) */
    fun getPhrasesByCategoryId(cid: Int): List<CommonPhraseDetail> {
        val list = mutableListOf<CommonPhraseDetail>()
        val db: SQLiteDatabase = dbHelper.openDatabase()

        try {
            val cursor: Cursor = db.rawQuery(
                "SELECT * FROM commonUsefulPhrasesDetails WHERE cid = ?",
                arrayOf(cid.toString())
            )

            if (cursor.moveToFirst()) {
                do {
                    list.add(cursorToModel(cursor))
                } while (cursor.moveToNext())
            }
            cursor.close()
        } finally {
            db.close()
        }
        return list
    }

    /** Cursor → Model */
    private fun cursorToModel(cursor: Cursor): CommonPhraseDetail {
        return CommonPhraseDetail(
            id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
            cid = cursor.getInt(cursor.getColumnIndexOrThrow("cid")),
            phrase = cursor.getString(cursor.getColumnIndexOrThrow("phrases"))
        )
    }
}