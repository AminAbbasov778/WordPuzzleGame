package com.example.wordsapp.home.domain.usecases

import com.example.wordsapp.core.domain.repositories.UserRepository
import javax.inject.Inject

class GetUsernameUseCase @Inject constructor(private val userRepository: UserRepository) {
    operator fun invoke() = userRepository.getUsernameFromLocal()

}