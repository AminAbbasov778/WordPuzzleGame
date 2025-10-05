package com.example.wordsapp.home.data

import com.google.firebase.Timestamp

data class Room(
   val roomId: String = "",
    val roomName: String = "",
    val status: String = "",
    val maxPlayers: Int = 0,
    val difficulty: String = "",
    val language: String = "",
    val createdAt: Timestamp? = null
)