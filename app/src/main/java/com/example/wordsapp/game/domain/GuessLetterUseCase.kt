package com.example.wordsapp.game.domain

import com.example.wordsapp.core.data.models.GuessLetter
import com.example.wordsapp.core.domain.repositories.WordsRepository
import com.example.wordsapp.core.network.SocketHandler
import javax.inject.Inject

class GuessLetterUseCase @Inject constructor (private val repository: WordsRepository) {
    operator fun invoke(guessLetter: GuessLetter) = repository.guessLetter(guessLetter)
}
