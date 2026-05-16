package com.example.advanceenglishdictionary.models

data class Conversation(
    val title: String,
    val conversationText: String,
    val category: String
)

data class DialogueLine(
    val name: String,
    val text: String,
    val isLeft: Boolean
)
