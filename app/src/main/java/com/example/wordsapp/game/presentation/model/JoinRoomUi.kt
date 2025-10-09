package com.example.wordsapp.game.presentation.model

data class JoinRoomUi(val roomId: String,
                      val userId: String,
                      val username: String,
                      val difficulty: String,
                      val language: String
) {
}