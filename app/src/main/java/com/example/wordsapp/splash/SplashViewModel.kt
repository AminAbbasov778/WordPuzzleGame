package com.example.wordsapp.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wordsapp.home.domain.usecases.GetUsernameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(private val getUsernameUseCase: GetUsernameUseCase): ViewModel() {

    var _state = MutableStateFlow<SplashState>(SplashState())
    val state: StateFlow<SplashState> = _state

    init {
        getUsername()
    }

    fun getUsername(){
        _state.value = _state.value.copy(isLoaded = true)
        viewModelScope.launch {
            getUsernameUseCase().collect{
                delay(2000)
                if(it.isNullOrEmpty()){
                    _state.value = _state.value.copy(isSignedIn = false, isLoaded = false)
                }else{
                    _state.value = _state.value.copy(isSignedIn = true, isLoaded = false)

                }
            }

    }

}
}