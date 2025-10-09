package com.example.wordsapp.home.domain.model

import com.google.firebase.Timestamp

data class RoomModel( val roomId: String = "",
                 val roomName: String = "",
                 val status: String = "",
                 val maxPlayers: Int = 0,
                 val difficulty: String = "",
                 val language: String = "",
                 val createdAt: Timestamp? = null) {
}