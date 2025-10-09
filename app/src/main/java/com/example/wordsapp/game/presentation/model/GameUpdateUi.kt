package com.example.wordsapp.game.presentation.model

data class GameUpdateUi(
    val discovered: List<String>?,
    val guessedBy: String,
    val guessedLetter: String,
    val correct: Boolean,
    val playerScore: Int
)