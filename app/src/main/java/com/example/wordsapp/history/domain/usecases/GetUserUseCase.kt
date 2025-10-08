package com.example.wordsapp.history.domain.usecases

import com.example.wordsapp.history.domain.repository.HistoryRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val repository: HistoryRepository) {
    suspend operator fun invoke(userId: String) = repository.getUser(userId)
}
