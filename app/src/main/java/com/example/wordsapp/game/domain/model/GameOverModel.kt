package com.example.wordsapp.game.domain.model

data class GameOverModel(
    val winner: String?,
    val word: String,
    val hasWinner: Boolean
)