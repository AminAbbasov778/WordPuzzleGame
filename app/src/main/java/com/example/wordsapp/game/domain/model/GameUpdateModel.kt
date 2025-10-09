package com.example.wordsapp.game.domain.model

data class GameUpdateModel(
    val discovered: List<String>?,
    val guessedBy: String,
    val guessedLetter: String,
    val correct: Boolean,
    val playerScore: Int
)