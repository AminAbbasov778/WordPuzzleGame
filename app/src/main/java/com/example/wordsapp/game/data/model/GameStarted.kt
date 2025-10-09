package com.example.wordsapp.game.data.model

data class GameStarted(
    val wordLength: Int,
    val players: List<Player>
)