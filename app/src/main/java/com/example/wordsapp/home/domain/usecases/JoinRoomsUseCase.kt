package com.example.wordsapp.home.domain.usecases

import com.example.wordsapp.home.domain.model.JoinRoomModel
import com.example.wordsapp.home.domain.repository.RoomsRepository
import javax.inject.Inject

class JoinRoomsUseCase @Inject constructor(private val roomsRepository: RoomsRepository) {
    operator fun invoke(joinRoomModel: JoinRoomModel) = roomsRepository.joinRoom(joinRoomModel)

}