package com.example.advanceenglishdictionary.dao

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.advanceenglishdictionary.database.PhrasesDatabaseHelper
import com.example.advanceenglishdictionary.models.IVerb

class IVerbsDao(private val context: Context) {

    private val dbHelper = PhrasesDatabaseHelper(context)

    // Data class remains separate in IVerb.kt

    /** Get all verbs */
    fun getAllIVerbs(): List<IVerb> {
        val verbs = mutableListOf<IVerb>()
        val db = dbHelper.openDatabase()
        try {
            val cursor: Cursor = db.rawQuery("SELECT * FROM IVerbsFull", null)
            if (cursor.moveToFirst()) {
                do {
                    verbs.add(cursorToVerb(cursor))
                } while (cursor.moveToNext())
            }
            cursor.close()
        } finally {
            db.close()
        }
        return verbs
    }


    /** Helper to convert cursor to model */
    private fun cursorToVerb(cursor: Cursor): IVerb {
        return IVerb(
            baseForm = cursor.getString(cursor.getColumnIndexOrThrow("baseform")),
            pastSimple = cursor.getString(cursor.getColumnIndexOrThrow("pastsimple")),
            pastPart = cursor.getString(cursor.getColumnIndexOrThrow("pastpart")),
            person3rd = cursor.getString(cursor.getColumnIndexOrThrow("person3rd")),
            gerund = cursor.getString(cursor.getColumnIndexOrThrow("gerund")),
            definition = cursor.getString(cursor.getColumnIndexOrThrow("definition")),
            favorite = cursor.getInt(cursor.getColumnIndexOrThrow("favorite"))
        )
    }
}