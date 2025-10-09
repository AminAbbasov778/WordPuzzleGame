package com.example.wordsapp.home.presentation.model

import com.example.wordsapp.core.presentation.enums.Difficulty
import com.example.wordsapp.core.presentation.enums.Language
import com.example.wordsapp.core.presentation.enums.Status


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