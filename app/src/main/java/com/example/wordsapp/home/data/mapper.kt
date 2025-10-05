package com.example.wordsapp.home.data

import com.example.wordsapp.core.data.models.JoinRoom
import com.example.wordsapp.core.data.models.PlayerJoined
import com.example.wordsapp.core.data.models.RoomUpdate
import com.example.wordsapp.home.domain.JoinRoomModel
import com.example.wordsapp.home.domain.PlayerJoinedModel
import com.example.wordsapp.home.domain.RoomModel
import com.example.wordsapp.home.domain.RoomUpdateModel
import com.example.wordsapp.home.presentation.RoomUi
import com.example.wordsapp.home.presentation.RoomUpdateUi
import java.text.SimpleDateFormat


fun Room.toDomain(): RoomModel {
    return RoomModel(
        roomId = roomId,
        roomName = roomName,
        status = status,
        maxPlayers = maxPlayers,
        difficulty = difficulty,
        language = language,
        createdAt = createdAt
    )
}

fun RoomModel.toEntity(): Room {
    return Room(
        roomId = roomId,
        roomName = roomName,
        status = status,
        maxPlayers = maxPlayers,
        difficulty = difficulty,
        language = language,

    )
}

fun JoinRoomModel.toData(): JoinRoom = JoinRoom(
    roomId = this.roomId,
    userId = this.userId,
    username = this.username,
    difficulty = this.difficulty,
    language = this.language
)

fun RoomUpdateModel.toData(): RoomUpdate = RoomUpdate(
    roomId = this.roomId,
    userId = this.userId,
    type = this.action
)

fun RoomUpdate.toDomain(): RoomUpdateModel = RoomUpdateModel(
    roomId = this.roomId ?: "1",
    userId = this.userId ?: "2",
    action = this.type ?: ""
)

fun PlayerJoined.toDomain(): PlayerJoinedModel {
    return PlayerJoinedModel(
        userId = this.userId,
        username = this.username

    )

}

