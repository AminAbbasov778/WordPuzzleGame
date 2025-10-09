package com.example.wordsapp.home.domain.model

data class RoomUpdateModel(
    val roomId: String,
    val userId: String,
    val action: String
)