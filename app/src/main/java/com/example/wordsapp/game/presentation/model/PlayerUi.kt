package com.example.wordsapp.game.presentation.model

data class PlayerUi(
    val id: String,
    val name: String,
    val ready: Boolean,
    val eliminated: Boolean,
    val score: Int
)