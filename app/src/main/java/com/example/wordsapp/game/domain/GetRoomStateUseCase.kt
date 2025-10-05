package com.example.wordsapp.game.domain

import com.example.wordsapp.core.domain.repositories.WordsRepository
import javax.inject.Inject

class GetRoomStateUseCase @Inject constructor(private val wordsRepository: WordsRepository) {
    operator fun invoke() = wordsRepository.roomStateFlow()

}