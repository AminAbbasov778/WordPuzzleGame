package com.example.wordsapp.home.domain.repository

import com.example.wordsapp.game.domain.model.HomeStateModel
import com.example.wordsapp.home.data.model.room.Room
import com.example.wordsapp.home.domain.model.JoinRoomModel
import com.example.wordsapp.home.domain.model.RoomUpdateModel
import kotlinx.coroutines.flow.Flow

interface RoomsRepository {
    fun getRooms(status: Int): Flow<Result<Room>>
    fun connectSocket()
    fun disconnectSocket()
    fun joinRoom(joinRoom: JoinRoomModel)
    fun roomStateFlow(): Flow<HomeStateModel>
    fun isConnected(): Boolean
    fun roomUpdateFlow(): Flow<RoomUpdateModel>
}