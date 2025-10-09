package com.example.wordsapp.game.domain.usecase

import com.example.wordsapp.game.domain.model.LeaveRoomModel
import com.example.wordsapp.game.domain.repository.GameRepository
import javax.inject.Inject

class LeaveRoomUseCase  @Inject constructor(private val gameRepository: GameRepository) {
    operator fun invoke(leaveRoom: LeaveRoomModel) = gameRepository.leaveRoom(leaveRoom)
}