package com.example.wordsapp.game.domain.usecase

import com.example.wordsapp.game.domain.model.ReadyModel
import com.example.wordsapp.game.domain.repository.GameRepository
import javax.inject.Inject

class ReadyUseCase @Inject constructor(private val gameRepository: GameRepository) {
    operator fun invoke(ready: ReadyModel) = gameRepository.ready(ready)
}