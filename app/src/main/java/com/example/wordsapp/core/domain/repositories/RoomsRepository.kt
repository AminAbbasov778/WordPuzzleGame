package com.example.wordsapp.core.domain.repositories

import com.example.wordsapp.home.domain.RoomModel
import kotlinx.coroutines.flow.Flow

interface RoomsRepository {
    fun getWaitingRooms(): Flow<List<RoomModel>>
    fun getRooms(status: Int): Flow<Result<com.example.wordsapp.home.data.module.room.Room>>}


