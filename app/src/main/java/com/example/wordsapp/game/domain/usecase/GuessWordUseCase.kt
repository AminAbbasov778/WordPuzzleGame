package com.example.wordsapp.game.domain.usecase

import com.example.wordsapp.game.domain.model.GuessWordModel
import com.example.wordsapp.game.domain.repository.GameRepository
import javax.inject.Inject

class GuessWordUseCase @Inject constructor (private val gameRepository: GameRepository) {
    operator fun invoke(guessWord: GuessWordModel) = gameRepository.guessWord(guessWord)
}