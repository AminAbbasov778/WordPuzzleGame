package com.example.wordsapp.game.domain.model

import com.example.wordsapp.game.presentation.model.PlayerUi

data class HomeStateModel(val roomId: String,
                          val players: List<PlayerModel>,
                          val started: Boolean,
                          val discovered: List<String>,
                          val wrongGuesses: Int,
                          val currentTurn: String?) {
}