package com.example.wordsapp.game.domain

import com.example.wordsapp.core.data.models.Ready
import com.example.wordsapp.core.domain.repositories.WordsRepository
import javax.inject.Inject

class ReadyUseCase @Inject constructor(private val repository: WordsRepository) {
    operator fun invoke(ready: Ready) = repository.ready(ready)
}