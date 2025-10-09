package com.example.wordsapp.splash.state

import com.example.wordsapp.core.presentation.base.UIState

data class SplashState(val isSignedIn: Boolean = false, val isLoaded: Boolean = false) : UIState{
}