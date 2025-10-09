package com.example.wordsapp.game.domain.model

data class GameStartedModel(
    val wordLength: Int,
    val players: List<PlayerModel>
)