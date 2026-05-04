package com.example.advanceenglishdictionary.dao

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.advanceenglishdictionary.database.PhrasesDatabaseHelper
import com.example.advanceenglishdictionary.models.CommonExpressionsDetail

class CommonExpressionsDetailDao(context: Context) {

    private val TABLE_NAME = "commonExpressionsDetails"
    private val dbHelper = PhrasesDatabaseHelper(context)

    /** Get all expressions detail */
    fun getAllDetails(): List<CommonExpressionsDetail> {
        val list = mutableListOf<CommonExpressionsDetail>()
        val db: SQLiteDatabase = dbHelper.openDatabase()

        try {
            // Ensure the table exists before querying
            createTableIfNotExists(db)

            val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
            cursor.use {
                while (it.moveToNext()) {
                    list.add(it.toCommonExpressionDetail())
                }
            }
        } finally {
            db.close()
        }

        return list
    }

    /** Get expressions detail by category id (cid) */
    fun getDetailsByCid(cid: Int): List<CommonExpressionsDetail> {
        val list = mutableListOf<CommonExpressionsDetail>()
        val db: SQLiteDatabase = dbHelper.openDatabase()

        try {
            createTableIfNotExists(db) // make sure table exists

            val cursor = db.rawQuery(
                "SELECT * FROM $TABLE_NAME WHERE cid = ?",
                arrayOf(cid.toString())
            )
            cursor.use {
                while (it.moveToNext()) {
                    list.add(it.toCommonExpressionDetail())
                }
            }
        } finally {
            db.close()
        }

        return list
    }

    /** Cursor → Model mapper */
    private fun Cursor.toCommonExpressionDetail(): CommonExpressionsDetail {
        return CommonExpressionsDetail(
            id = getInt(getColumnIndexOrThrow("id")),
            cid = getInt(getColumnIndexOrThrow("cid")),
            phrase = getString(getColumnIndexOrThrow("phrase")),
            description = getString(getColumnIndexOrThrow("description"))
        )
    }

    /** Ensure the table exists, create if missing */
    private fun createTableIfNotExists(db: SQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE IF NOT EXISTS $TABLE_NAME (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                cid INTEGER NOT NULL,
                phrase TEXT NOT NULL,
                description TEXT
            )
        """.trimIndent())
        Log.d("DAO", "$TABLE_NAME ensured to exist")
    }
}