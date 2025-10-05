package com.example.wordsapp.home.domain.usecases

import com.example.wordsapp.core.domain.repositories.RoomsRepository
import javax.inject.Inject

class GetWaitingRoomsUseCase @Inject constructor(private  val roomsRepository: RoomsRepository) {
    operator fun invoke()= roomsRepository.getWaitingRooms()
}