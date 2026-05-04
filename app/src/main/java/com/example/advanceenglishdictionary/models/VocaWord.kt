package com.example.advanceenglishdictionary.models

data class VocaWord(
    val id: Int,
    val keyword: String,
    val type: String,
    val definition: String,
    val example: String,
    val favorite: Int
)