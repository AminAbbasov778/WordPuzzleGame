package com.example.wordsapp.game.domain.model

data class JoinRoomModel(val roomId: String,
                         val userId: String,
                         val username: String,
                         val difficulty: String,
                         val language: String
) {
}