package com.example.wordsapp.game.presentation.model

import com.example.wordsapp.game.domain.model.PlayerModel

data class HomeStateUi(val roomId: String,
                       val players: List<PlayerUi>,
                       val started: Boolean,
                       val discovered: List<String>,
                       val wrongGuesses: Int,
                       val currentTurn: String?) {
}