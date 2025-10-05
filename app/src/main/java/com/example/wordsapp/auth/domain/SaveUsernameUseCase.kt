package com.example.wordsapp.auth.domain

import com.example.wordsapp.core.domain.repositories.UserRepository
import javax.inject.Inject

class SaveUsernameUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(username: String) {
        userRepository.saveUsername(username)
    }

}