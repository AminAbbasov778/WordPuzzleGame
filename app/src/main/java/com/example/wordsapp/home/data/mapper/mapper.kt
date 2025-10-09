package com.example.wordsapp.home.data.mapper

import com.example.wordsapp.home.data.model.RoomUpdate
import com.example.wordsapp.home.data.model.JoinRoom
import com.example.wordsapp.game.data.model.PlayerJoined
import com.example.wordsapp.home.domain.model.JoinRoomModel
import com.example.wordsapp.home.domain.model.PlayerJoinedModel
import com.example.wordsapp.home.domain.model.RoomUpdateModel





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

