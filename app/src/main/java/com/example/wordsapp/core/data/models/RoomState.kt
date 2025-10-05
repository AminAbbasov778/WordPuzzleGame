package com.example.wordsapp.core.data.models

data class RoomState( val roomId: String,
                      val players: List<Player>,
                      val started: Boolean,
                      val discovered: List<String>,
                      val wrongGuesses: Int,
                      val currentTurn: String?) {
}