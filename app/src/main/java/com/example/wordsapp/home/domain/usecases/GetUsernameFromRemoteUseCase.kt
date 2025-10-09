package com.example.wordsapp.home.domain.usecases

import com.example.wordsapp.core.domain.repositories.UserRepository
import javax.inject.Inject

class GetUsernameFromRemoteUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke() = userRepository.getUsernameFromRemote()


}