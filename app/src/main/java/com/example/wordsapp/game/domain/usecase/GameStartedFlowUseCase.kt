package com.example.wordsapp.game.domain.usecase

import com.example.wordsapp.game.domain.repository.GameRepository
import javax.inject.Inject

class GameStartedFlowUseCase @Inject constructor (private val gameRepository: GameRepository) {
    operator fun invoke() = gameRepository.gameStartedFlow()
}