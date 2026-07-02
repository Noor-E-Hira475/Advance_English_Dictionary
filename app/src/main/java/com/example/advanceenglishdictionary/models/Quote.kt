package com.example.advanceenglishdictionary.models

data class Quote(
    val quote: String,
    val author: String
)

data class Quotes(
    val inspiration: List<Quote>,
    val love: List<Quote>,
    val happiness: List<Quote>,
    val success: List<Quote>,
    val wisdom: List<Quote>,
    val friendship: List<Quote>,
    val life: List<Quote>,
    val motivation: List<Quote>,
    val courage: List<Quote>,
    val change: List<Quote>
)

data class QuotesResponse(
    val quotes: Quotes
)