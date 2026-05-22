package com.example.advanceenglishdictionary.models

data class QuizQuestion(
    val subLevel: Int,
    val qNumber: Int,
    val qContent: String,
    val answerA: String,
    val answerB: String,
    val answerC: String,
    val answerD: String,
    val correctAnswer: String,
    val passed: Int
)
