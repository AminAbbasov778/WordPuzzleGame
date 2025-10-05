package com.example.wordsapp.home.domain.usecases

import com.example.wordsapp.auth.domain.AuthRepository
import javax.inject.Inject

class SignInAnonymouslyUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(): Result<Unit> {
        return authRepository.signInAnonymously()
    }

}