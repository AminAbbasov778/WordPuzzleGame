package com.example.wordsapp.core.domain

import com.example.wordsapp.core.domain.repositories.WordsRepository
import javax.inject.Inject

class ConnectSocketUseCase @Inject constructor(
private val wordsRepository: WordsRepository
) {
    operator fun invoke() = wordsRepository.connectSocket()

}