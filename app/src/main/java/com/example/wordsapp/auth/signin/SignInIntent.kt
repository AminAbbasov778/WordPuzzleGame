package com.example.wordsapp.auth.signin

sealed class SignInIntent {
    data class EnterEmail(val email: String): SignInIntent()
    data class EnterPassword(val password: String): SignInIntent()
     object ChangePasswordVisibility: SignInIntent()
    object Submit: SignInIntent()
    object ContinueAsGuest: SignInIntent()
}