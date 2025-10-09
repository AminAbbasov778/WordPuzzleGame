package com.example.wordsapp.auth.signup

import com.example.wordsapp.core.presentation.base.UIState

data class SignUpState(
    val email: String = "",
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSignedUp: Boolean = false,
    val isPasswordVisible: Boolean = false,



): UIState