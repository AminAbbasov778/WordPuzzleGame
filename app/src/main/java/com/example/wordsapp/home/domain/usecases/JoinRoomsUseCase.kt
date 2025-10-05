package com.example.wordsapp.home.domain.usecases

import com.example.wordsapp.core.domain.repositories.WordsRepository
import com.example.wordsapp.home.domain.JoinRoomModel
import javax.inject.Inject

class JoinRoomsUseCase @Inject constructor(private val wordsRepository: WordsRepository) {
    operator fun invoke(joinRoomModel: JoinRoomModel) = wordsRepository.joinRoom(joinRoomModel)

}