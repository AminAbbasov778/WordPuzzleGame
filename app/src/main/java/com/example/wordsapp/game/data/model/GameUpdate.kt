package com.example.wordsapp.game.data.model

data class GameUpdate(
    val discovered: List<String>?,
    val guessedBy: String,
    val guessedLetter: String,
    val correct: Boolean,
    val playerScore: Int
)