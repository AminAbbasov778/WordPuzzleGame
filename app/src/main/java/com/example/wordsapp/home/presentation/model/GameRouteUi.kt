package com.example.wordsapp.home.presentation.model

import kotlinx.serialization.Serializable


@Serializable
data class GameRouteUi( val roomId: String = "",
                   val roomName: String = "",
                   val status: String = "WAITING",
                   val maxPlayers: Int = 0,
                   val currentPlayers: Int = 0,
                   val difficulty: String = "EASY",
                   val language: String = "EN" ,
                   var createdAt: String = "",
                   val isJoinClicked: Boolean = false,
                   val     username: String = "",
                   val userUid: String = ""
    ) {
}