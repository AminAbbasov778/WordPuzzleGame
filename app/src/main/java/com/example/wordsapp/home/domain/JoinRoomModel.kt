package com.example.wordsapp.home.domain

data class JoinRoomModel( val roomId: String,
                     val userId: String,
                     val username: String,
                     val difficulty: String,
                     val language: String) {
}