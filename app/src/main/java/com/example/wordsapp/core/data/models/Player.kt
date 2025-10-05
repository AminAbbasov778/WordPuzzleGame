package com.example.wordsapp.core.data.models

data class Player(
    val id: String,
    val name: String,
    val ready: Boolean,
    val eliminated: Boolean,
    val score: Int
)