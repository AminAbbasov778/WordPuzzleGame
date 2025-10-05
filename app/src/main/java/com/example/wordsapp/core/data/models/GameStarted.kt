package com.example.wordsapp.core.data.models

data class GameStarted(
    val wordLength: Int,
    val players: List<Player>
)