package com.example.wordsapp.history.presentation.history

import com.example.wordsapp.history.data.models.history.Winner

data class GameUi(
    val createdAt: String,
    val difficulty: String,
    val id: String,
    val language: String,
    val players: List<PlayerUi>,
    val roomId: String,
    val winner: WinnerUi,
    val word: String
,val isWordVisible: Boolean = false

) {

}