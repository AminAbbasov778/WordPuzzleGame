package com.example.wordsapp.auth.domain.usecases

import com.example.wordsapp.auth.domain.repository.AuthRepository
import javax.inject.Inject

class GetUserUidUseCase @Inject constructor(private val authRepository: AuthRepository) {
    operator fun invoke(): String? {
        return authRepository.getUserUid()
    }

}