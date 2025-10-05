package com.example.wordsapp.game.domain

import com.example.wordsapp.core.data.models.GuessWord
import com.example.wordsapp.core.domain.repositories.WordsRepository
import javax.inject.Inject

class GuessWordUseCase @Inject constructor (private val repository: WordsRepository) {
    operator fun invoke(guessWord: GuessWord) = repository.guessWord(guessWord)
}