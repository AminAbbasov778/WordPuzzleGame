package com.example.wordsapp.auth.domain.usecases

import com.example.wordsapp.core.domain.repositories.UserRepository
import javax.inject.Inject

class SaveUsernameToRemoteUseCase @Inject constructor(private val userRepository: UserRepository)  {
    suspend operator fun invoke(username: String) {
        userRepository.saveUsernameToRemote(username)
    }

}