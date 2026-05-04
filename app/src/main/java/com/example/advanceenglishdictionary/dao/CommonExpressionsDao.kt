package com.example.advanceenglishdictionary.dao

import android.content.Context
import android.database.Cursor
import com.example.advanceenglishdictionary.database.PhrasesDatabaseHelper
import com.example.advanceenglishdictionary.models.CommonExpressions

class CommonExpressionsDao(context: Context) {

    private val TABLE_NAME = "commonExpressions"
    private val dbHelper = PhrasesDatabaseHelper(context)

    fun getAllCategories(): List<CommonExpressions> {
        val list = mutableListOf<CommonExpressions>()
        val db = dbHelper.openDatabase()

        try {
            val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
            cursor.use {
                while (it.moveToNext()) {
                    list.add(it.toCommonExpression())
                }
            }
        } finally {
            db.close()
        }

        return list
    }

    fun getCategoryByCid(cid: Int): CommonExpressions? {
        val db = dbHelper.openDatabase()

        return try {
            val cursor = db.rawQuery(
                "SELECT * FROM $TABLE_NAME WHERE cid = ?",
                arrayOf(cid.toString())
            )

            cursor.use {
                if (it.moveToFirst()) it.toCommonExpression() else null
            }
        } finally {
            db.close()
        }
    }

    private fun Cursor.toCommonExpression(): CommonExpressions {
        return CommonExpressions(
            id = getInt(getColumnIndexOrThrow("id")),
            cid = getInt(getColumnIndexOrThrow("cid")),
            categoryName = getString(getColumnIndexOrThrow("categoryName")),
            categoryDescription = getString(getColumnIndexOrThrow("categoryDescription"))
        )
    }
}