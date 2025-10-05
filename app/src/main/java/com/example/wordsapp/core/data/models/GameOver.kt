package com.example.wordsapp.core.data.models

data class GameOver(
    val winner: String?,
    val word: String,
    val hasWinner: Boolean
)