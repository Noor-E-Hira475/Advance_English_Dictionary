package com.example.advanceenglishdictionary.dao

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.advanceenglishdictionary.database.PhrasesDatabaseHelper
import com.example.advanceenglishdictionary.models.QuizQuestion

class QuizDao(context: Context) {

    private val dbHelper = PhrasesDatabaseHelper(context)
    private val TABLE_NAME = "ReadTestQuiz"

    /** Get 10 random questions for a specific sublevel */
    fun getQuestions(subLevel: Int): List<QuizQuestion> {
        val list = mutableListOf<QuizQuestion>()
        val db: SQLiteDatabase = dbHelper.openDatabase()

        try {
            val cursor = db.rawQuery(
                "SELECT * FROM $TABLE_NAME WHERE SubLevel = ? ORDER BY RANDOM() LIMIT 10",
                arrayOf(subLevel.toString())
            )

            cursor.use {
                while (it.moveToNext()) {
                    list.add(it.toQuizQuestion())
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.close()
        }
        return list
    }

    private fun Cursor.toQuizQuestion(): QuizQuestion {
        return QuizQuestion(
            subLevel = getInt(getColumnIndexOrThrow("SubLevel")),
            qNumber = getInt(getColumnIndexOrThrow("QNumber")),
            qContent = getString(getColumnIndexOrThrow("QContent")),
            answerA = getString(getColumnIndexOrThrow("AnswerA")),
            answerB = getString(getColumnIndexOrThrow("AnswerB")),
            answerC = getString(getColumnIndexOrThrow("AnswerC")),
            answerD = getString(getColumnIndexOrThrow("AnswerD")),
            correctAnswer = getString(getColumnIndexOrThrow("CorrectAnswer")),
            passed = getInt(getColumnIndexOrThrow("Passed"))
        )
    }
}
