package com.example.wordsapp.game

data class GameUpdateUi(
    val discovered: List<String>,
    val guessedBy: String,
    val guessedLetter: String,
    val correct: Boolean,
    val playerScore: Int
)