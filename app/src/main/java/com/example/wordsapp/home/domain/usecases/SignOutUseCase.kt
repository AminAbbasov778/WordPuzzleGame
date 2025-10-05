package com.example.wordsapp.home.domain.usecases

import com.example.wordsapp.auth.domain.AuthRepository
import javax.inject.Inject

class SignOutUseCase @Inject constructor(private val authRepository: AuthRepository) {
    operator fun invoke() {
        authRepository.signOut()
    }

}