package com.example.wordsapp.home.domain.usecases

import com.example.wordsapp.core.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUsernameUseCase @Inject constructor(private val userRepository: UserRepository) {
    operator fun invoke(): Flow<String?> = userRepository.getUsernameFromLocal()

}