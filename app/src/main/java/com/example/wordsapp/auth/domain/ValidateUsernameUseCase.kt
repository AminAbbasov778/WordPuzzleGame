package com.example.wordsapp.auth.domain

import javax.inject.Inject

class ValidateUsernameUseCase @Inject constructor()  {
    operator fun invoke(username: String): Boolean {
        return username.isNotBlank() && username.length >= 3
    }
}