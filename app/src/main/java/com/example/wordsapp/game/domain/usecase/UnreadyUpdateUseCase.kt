package com.example.wordsapp.game.domain.usecase

import com.example.wordsapp.game.domain.model.UnreadyUpdateModel
import com.example.wordsapp.game.domain.repository.GameRepository
import javax.inject.Inject

class UnreadyUpdateUseCase @Inject constructor(private val gameRepository: GameRepository) {
    operator fun invoke(unready: UnreadyUpdateModel) = gameRepository.unreadyUpdate(unready)
}