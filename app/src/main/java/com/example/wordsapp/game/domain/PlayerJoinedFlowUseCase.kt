package com.example.wordsapp.game.domain

import com.example.wordsapp.core.domain.repositories.WordsRepository
import javax.inject.Inject

class PlayerJoinedFlowUseCase @Inject constructor(private val wordsRepository: WordsRepository) {

    operator fun invoke() = wordsRepository.playerJoinedFlow()

}