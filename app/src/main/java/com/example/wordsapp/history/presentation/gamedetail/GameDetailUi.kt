package com.example.wordsapp.history.presentation.gamedetail

data class GameDetailUi(
    val createdAt: String,
    val difficulty: String,
    val id: String,
    val language: String,
    val players: List<GameDetailPlayerUi>,
    val roomId: String,
    val winner: GameDetailWinnerUi,
    val word: String,
    val roomName: String,
    val isWordVisible: Boolean
) {
}

