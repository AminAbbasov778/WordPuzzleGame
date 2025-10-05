package com.example.wordsapp.game.domain

import com.example.wordsapp.core.data.models.LeaveRoom
import com.example.wordsapp.core.domain.repositories.WordsRepository
import com.example.wordsapp.core.network.SocketHandler
import javax.inject.Inject

class LeaveRoomUseCase  @Inject constructor(private val repository: WordsRepository) {
    operator fun invoke(leaveRoom: LeaveRoom) = repository.leaveRoom(leaveRoom)
}