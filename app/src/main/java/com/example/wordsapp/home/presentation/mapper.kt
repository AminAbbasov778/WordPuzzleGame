package com.example.wordsapp.home.presentation

import com.example.wordsapp.core.data.models.GameUpdate
import com.example.wordsapp.core.utils.getRelativeTime
import com.example.wordsapp.game.GameUpdateUi
import com.example.wordsapp.home.domain.JoinRoomModel
import com.example.wordsapp.home.domain.PlayerJoinedModel
import com.example.wordsapp.home.domain.RoomModel
import com.example.wordsapp.home.domain.RoomUpdateModel
import toAction
import toDifficulty
import toLanguage
import toStatus

fun RoomModel.toUi(): RoomUi {
    return RoomUi(
        roomId = roomId,
        roomName = roomName,
        status = status.toStatus(),
        maxPlayers = maxPlayers,
        difficulty = difficulty.toDifficulty(),
        language = language.toLanguage(),
        createdAt = getRelativeTime(createdAt)
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

