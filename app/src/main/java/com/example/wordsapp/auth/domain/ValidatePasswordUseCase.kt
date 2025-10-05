package com.example.wordsapp.auth.domain

import javax.inject.Inject

class ValidatePasswordUseCase @Inject constructor()  {
    operator fun invoke(password: String): Boolean {
        return password.isNotBlank() && password.length >= 6
    }
}