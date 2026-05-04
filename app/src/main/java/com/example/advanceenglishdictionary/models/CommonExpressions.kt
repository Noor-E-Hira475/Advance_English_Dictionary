package com.example.advanceenglishdictionary.models

data class CommonExpressions(
    val id: Int,          // Primary Key (auto-generated)
    val cid: Int,             // Category identifier (logical/grouping id)
    val categoryName: String,
    val categoryDescription: String
)