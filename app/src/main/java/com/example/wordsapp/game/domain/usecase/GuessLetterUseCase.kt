package com.example.wordsapp.game.domain.usecase

import com.example.wordsapp.game.domain.model.GuessLetterModel
import com.example.wordsapp.game.domain.repository.GameRepository
import javax.inject.Inject

class GuessLetterUseCase @Inject constructor (private val repository: GameRepository) {
    operator fun invoke(guessLetter: GuessLetterModel) = repository.guessLetter(guessLetter)
}
