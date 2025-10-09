package com.example.wordsapp.game.domain.model

data class PlayerModel(
    val id: String,
    val name: String,
    val ready: Boolean,
    val eliminated: Boolean,
    val score: Int
)