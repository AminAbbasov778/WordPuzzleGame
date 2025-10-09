package com.example.wordsapp.auth.signup

import com.example.wordsapp.core.presentation.base.UIIntent

sealed class SignUpIntent : UIIntent {
    data class EnterEmail(val email: String): SignUpIntent()
    data class EnterUsername(val username: String): SignUpIntent()
    data class EnterPassword(val password: String): SignUpIntent()
    object SignUpClicked: SignUpIntent()
    object ContinueAsGuestClicked: SignUpIntent()
    object ChangePasswordVisibility: SignUpIntent()
    object SignInClicked: SignUpIntent()




}