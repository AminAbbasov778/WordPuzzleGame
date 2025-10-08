package com.example.wordsapp.home.presentation

import com.google.gson.annotations.SerializedName


data class RoomUi(
    val roomId: String = "",
    val roomName: String = "",
    val status: Status = Status.WAITING,
    val maxPlayers: Int = 0,
    val currentPlayers: Int = 0,
    val difficulty: Difficulty = Difficulty.EASY,
    val language: Language = Language.EN,
    var createdAt: String? = null,
    val winner: Boolean? = null,
    val word: Boolean? = null,
    val isJoinClicked: Boolean = false,
    val wrongGuesses: Int = 0,
    val hasWinner: Boolean = false


    ) {
}