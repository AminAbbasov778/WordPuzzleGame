package com.example.wordsapp.game.data.model

data class HomeState(val roomId: String,
                     val players: List<Player>,
                     val started: Boolean,
                     val discovered: List<String>,
                     val wrongGuesses: Int,
                     val currentTurn: String?) {
}