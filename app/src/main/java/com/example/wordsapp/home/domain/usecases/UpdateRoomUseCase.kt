package com.example.wordsapp.home.domain.usecases

import com.example.wordsapp.home.domain.repository.RoomsRepository
import javax.inject.Inject

class UpdateRoomUseCase @Inject constructor(private val roomsRepository: RoomsRepository) {
    suspend operator fun invoke() = roomsRepository.roomUpdateFlow()

}