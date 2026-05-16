package com.example.advanceenglishdictionary.dao

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.advanceenglishdictionary.database.PhrasesDatabaseHelper
import com.example.advanceenglishdictionary.models.Conversation

class ConversationDao(context: Context) {

    private val dbHelper = PhrasesDatabaseHelper(context)
    private val TABLE_NAME = "tbl_conversation"

    /** Get all topics for a specific category */
    fun getTopicsByCategory(categoryName: String): List<Conversation> {
        val list = mutableListOf<Conversation>()
        val db: SQLiteDatabase = dbHelper.openDatabase()

        try {
            val cursor = db.rawQuery(
                "SELECT title, conversation, category FROM $TABLE_NAME WHERE category = ?",
                arrayOf(categoryName)
            )
            
            cursor.use {
                while (it.moveToNext()) {
                    list.add(it.toConversation())
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.close()
        }
        return list
    }

    /** Get full conversation details by title */
    fun getConversationByTitle(title: String): Conversation? {
        var conversation: Conversation? = null
        val db: SQLiteDatabase = dbHelper.openDatabase()

        try {
            val cursor = db.rawQuery(
                "SELECT title, conversation, category FROM $TABLE_NAME WHERE title = ?",
                arrayOf(title)
            )
            
            cursor.use {
                if (it.moveToFirst()) {
                    conversation = it.toConversation()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.close()
        }
        return conversation
    }

    private fun Cursor.toConversation(): Conversation {
        return Conversation(
            title = getString(getColumnIndexOrThrow("title")),
            conversationText = getString(getColumnIndexOrThrow("conversation")),
            category = getString(getColumnIndexOrThrow("category"))
        )
    }
}
