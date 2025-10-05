package com.example.wordsapp.auth.signup

sealed class SignUpIntent {
    data class EnterEmail(val email: String): SignUpIntent()
    data class EnterUsername(val username: String): SignUpIntent()
    data class EnterPassword(val password: String): SignUpIntent()
    object SignUpClicked: SignUpIntent()
    object ContinueAsGuestClicked: SignUpIntent()
    object ChangePasswordVisibility: SignUpIntent()

}