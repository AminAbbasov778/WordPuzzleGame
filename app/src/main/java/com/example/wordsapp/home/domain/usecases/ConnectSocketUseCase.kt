package com.example.wordsapp.home.domain.usecases

import com.example.wordsapp.home.domain.repository.RoomsRepository
import javax.inject.Inject

class ConnectSocketUseCase @Inject constructor(
    private val roomsRepository: RoomsRepository
) {
    operator fun invoke() = roomsRepository.connectSocket()

}