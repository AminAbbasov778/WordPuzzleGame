package com.example.wordsapp.game.domain

import com.example.wordsapp.core.data.models.UnreadyUpdate
import com.example.wordsapp.core.domain.repositories.WordsRepository
import javax.inject.Inject

class UnreadyUpdateUseCase @Inject constructor(private val repository: WordsRepository) {
    operator fun invoke(unready: UnreadyUpdate) = repository.unreadyUpdate(unready)
}