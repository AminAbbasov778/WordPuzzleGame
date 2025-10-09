package com.example.wordsapp.game.data.model

data class Player(
    val id: String,
    val name: String,
    val ready: Boolean,
    val eliminated: Boolean,
    val score: Int
)