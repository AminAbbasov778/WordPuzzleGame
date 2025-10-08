package com.example.wordsapp.home.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.wordsapp.core.data.models.GameUpdate
import com.example.wordsapp.core.utils.formatDate
import com.example.wordsapp.core.utils.getRelativeTime
import com.example.wordsapp.core.utils.getTimeAgo
import com.example.wordsapp.game.GameUpdateUi
import com.example.wordsapp.home.data.module.room.Room
import com.example.wordsapp.home.data.module.room.RoomX
import com.example.wordsapp.home.domain.JoinRoomModel
import com.example.wordsapp.home.domain.PlayerJoinedModel
import com.example.wordsapp.home.domain.RoomModel
import com.example.wordsapp.home.domain.RoomUpdateModel
import toAction
import toDifficulty
import toLanguage
import toStatus

fun RoomX.toUi(): RoomUi{
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

