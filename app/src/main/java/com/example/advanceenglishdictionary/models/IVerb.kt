package com.example.advanceenglishdictionary.models

data class IVerb(
    val baseForm: String,
    val pastSimple: String,
    val pastPart: String,
    val person3rd: String,
    val gerund: String,
    val definition: String,
    val favorite: Int
)
