package com.example.wordsapp.game.data.model

data class GameOver(
    val winner: String?,
    val word: String,
    val hasWinner: Boolean
)