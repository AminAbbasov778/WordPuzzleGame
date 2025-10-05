package com.example.wordsapp.home.domain.usecases

import com.example.wordsapp.core.domain.repositories.WordsRepository
import javax.inject.Inject

class UpdateRoomUseCase @Inject constructor(private  val wordsRepository: WordsRepository) {
    suspend operator fun invoke() = wordsRepository.roomUpdateFlow()

}