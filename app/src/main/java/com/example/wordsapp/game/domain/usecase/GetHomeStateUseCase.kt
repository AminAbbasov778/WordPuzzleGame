package com.example.wordsapp.game.domain.usecase

import com.example.wordsapp.home.domain.repository.RoomsRepository
import javax.inject.Inject

class GetHomeStateUseCase @Inject constructor(private val roomsRepository: RoomsRepository) {
    operator fun invoke() = roomsRepository.roomStateFlow()

}