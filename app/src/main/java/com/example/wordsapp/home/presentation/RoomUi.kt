package com.example.wordsapp.home.presentation




data class RoomUi(
    val roomId: String = "",
    val roomName: String = "",
    val status: Status = Status.WAITING,
    val maxPlayers: Int = 0,
    val currentPlayers: Int = 0,
    val difficulty: Difficulty = Difficulty.EASY,
    val language: Language = Language.EN,
    var createdAt: String? = null,
    val isJoinClicked: Boolean = false,

    ) {
}