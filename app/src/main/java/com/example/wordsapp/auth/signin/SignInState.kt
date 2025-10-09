package com.example.wordsapp.auth.signin

import com.example.wordsapp.core.presentation.base.UIState

data class SignInState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val isSignedIn: Boolean = false,
    val error: String? = null,
    val isPasswordVisible: Boolean = false,
    val errorMessage: String? = null
): UIState