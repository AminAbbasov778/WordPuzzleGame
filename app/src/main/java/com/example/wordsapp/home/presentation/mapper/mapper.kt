package com.example.wordsapp.home.presentation.mapper

import com.example.wordsapp.core.presentation.util.toAction
import com.example.wordsapp.core.presentation.util.toDifficulty
import com.example.wordsapp.core.presentation.util.toLanguage
import com.example.wordsapp.core.presentation.util.toStatus
import com.example.wordsapp.core.utils.getTimeAgo
import com.example.wordsapp.home.data.model.room.RoomX
import com.example.wordsapp.home.domain.model.JoinRoomModel
import com.example.wordsapp.home.domain.model.PlayerJoinedModel
import com.example.wordsapp.home.domain.model.RoomUpdateModel
import com.example.wordsapp.home.presentation.model.JoinRoomUi
import com.example.wordsapp.game.presentation.model.PlayerJoinedUi
import com.example.wordsapp.home.presentation.model.RoomUi
import com.example.wordsapp.home.presentation.model.RoomUpdateUi

fun RoomX.toUi(): RoomUi {
    return RoomUi(
        roomId = this.roomId,
        roomName = this.roomName,
        status = this.status.toStatus(),

        maxPlayers = this.maxPlayers,

        currentPlayers = this.playerCount,
        difficulty = this.difficulty.toDifficulty(),

        language = this.language.toLanguage(),
        createdAt = getTimeAgo(this.createdAt.seconds.toLong()),
        hasWinner = this.hasWinner,
        winner = this.winner,
        word = this.word,
        wrongGuesses = this.wrongGuesses,


        )

}

fun JoinRoomUi.toDomain(): JoinRoomModel = JoinRoomModel(
    roomId = this.roomId,
    userId = this.userId,
    username = this.username,
    difficulty = this.difficulty,
    language = this.language
)

fun RoomUpdateModel.toUi(): RoomUpdateUi = RoomUpdateUi(
    roomId = this.roomId,
    userId = this.userId,
    action = this.action.toAction()
)

fun PlayerJoinedModel.toUi(): PlayerJoinedUi = PlayerJoinedUi(
    userId = this.userId,
    username = this.username,
    isReady = false

)

