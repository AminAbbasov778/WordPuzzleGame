package com.example.wordsapp.auth.domain

import javax.inject.Inject

class GetUserUidUseCase @Inject constructor(private val authRepository: AuthRepository) {
    operator fun invoke(): String? {
        return authRepository.getUserUid()
    }

}