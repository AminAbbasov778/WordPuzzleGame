package com.example.wordsapp.core.domain

import com.example.wordsapp.core.domain.repositories.WordsRepository
import javax.inject.Inject

class DisconnectUseCase @Inject constructor(private val wordsRepository: WordsRepository) {

    operator fun invoke() {
        wordsRepository.disconnectSocket()
    }

}