package com.example.wordsapp.auth.signin

import com.example.wordsapp.core.presentation.base.UIIntent

sealed class SignInIntent : UIIntent{
    data class EnterEmail(val email: String): SignInIntent()
    data class EnterPassword(val password: String): SignInIntent()
     object ChangePasswordVisibility: SignInIntent()
    object Submit: SignInIntent()
    object ContinueAsGuest: SignInIntent()
    object SignUpClicked: SignInIntent()

}