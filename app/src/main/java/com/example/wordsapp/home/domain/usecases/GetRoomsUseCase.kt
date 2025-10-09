package com.example.wordsapp.home.domain.usecases

import com.example.wordsapp.home.domain.repository.RoomsRepository
import javax.inject.Inject

class GetRoomsUseCase @Inject constructor(private  val roomsRepository: RoomsRepository) {
    operator fun invoke(status: Int) = roomsRepository.getRooms(status)

}