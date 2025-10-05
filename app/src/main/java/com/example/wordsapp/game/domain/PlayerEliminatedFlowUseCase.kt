package com.example.wordsapp.game.domain

import com.example.wordsapp.core.domain.repositories.WordsRepository
import javax.inject.Inject

class PlayerEliminatedFlowUseCase @Inject constructor (private val repository: WordsRepository) {
    operator fun invoke() = repository.playerEliminatedFlow()
}