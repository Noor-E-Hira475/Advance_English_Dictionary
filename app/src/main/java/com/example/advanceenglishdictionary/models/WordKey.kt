package com.example.advanceenglishdictionary.models

import java.io.Serializable

data class WordKey(
    val idRef: Int,
    val word: String,
    val wordasID: String? = null
) : Serializable
