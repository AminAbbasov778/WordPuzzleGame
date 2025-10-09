package com.example.wordsapp.splash.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wordsapp.core.presentation.base.BaseViewModel
import com.example.wordsapp.home.domain.usecases.GetUsernameUseCase
import com.example.wordsapp.splash.intent.SplashIntent
import com.example.wordsapp.splash.navigation.SplashNavigation
import com.example.wordsapp.splash.navigation.SplashNavigation.*
import com.example.wordsapp.splash.screen.SplashScreen
import com.example.wordsapp.splash.state.SplashState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val getUsernameUseCase: GetUsernameUseCase,

): BaseViewModel<SplashState, SplashIntent, SplashNavigation>() {

    override val initialState: SplashState
        get() = SplashState()
    override fun OnEvent(event: SplashIntent) {

    }

    init {
        getUsername()
    }

    fun getUsername(){
        updateState {  it.copy(isLoaded = true)

        }
        viewModelScope.launch {
            getUsernameUseCase().collect{
                delay(2000)
                if(it.isNullOrEmpty()){
                   updateState {
                       it.copy( isLoaded = false)

                   }
                    navigate(SplashNavigation.SplashScreenToSignInScreen)
                }else{
                    updateState {
                       it.copy( isLoaded = false)
                    }
                    navigate(SplashNavigation.SplashScreenToHomeScreen)


                }
            }

    }

}
}