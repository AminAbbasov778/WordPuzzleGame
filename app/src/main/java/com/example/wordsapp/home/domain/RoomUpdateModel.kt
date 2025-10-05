package com.example.wordsapp.home.domain

data class RoomUpdateModel(
    val roomId: String,
    val userId: String,
    val action: String
)