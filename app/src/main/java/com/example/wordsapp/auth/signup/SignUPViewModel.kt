package com.example.wordsapp.auth.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wordsapp.auth.domain.GetUserUidUseCase
import com.example.wordsapp.auth.domain.SaveUsernameToRemoteUseCase
import com.example.wordsapp.auth.domain.SaveUsernameUseCase
import com.example.wordsapp.auth.domain.SignUpUseCase
import com.example.wordsapp.auth.domain.ValidateEmailUseCase
import com.example.wordsapp.auth.domain.ValidatePasswordUseCase
import com.example.wordsapp.auth.domain.ValidateUsernameUseCase
import com.example.wordsapp.home.domain.usecases.SignInAnonymouslyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val saveUsernameUseCase: SaveUsernameUseCase,
    private val saveUsernameToRemoteUseCase: SaveUsernameToRemoteUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val validateUsernameUseCase: ValidateUsernameUseCase,
    private val signInAnonymouslyUseCase: SignInAnonymouslyUseCase,
    private val getUserUidUseCase: GetUserUidUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(SignUpState())
    val state: StateFlow<SignUpState> = _state

    fun onIntent(intent: SignUpIntent) {
        when (intent) {
            is SignUpIntent.EnterEmail -> _state.value = _state.value.copy(email = intent.email)
            is SignUpIntent.EnterUsername -> _state.value =
                _state.value.copy(username = intent.username)

            is SignUpIntent.EnterPassword -> _state.value =
                _state.value.copy(password = intent.password)

            is SignUpIntent.SignUpClicked -> signUp()
            is SignUpIntent.ChangePasswordVisibility -> _state.value =
                _state.value.copy(isPasswordVisible = !_state.value.isPasswordVisible)

            SignUpIntent.ContinueAsGuestClicked -> continueAsGuest()
        }
    }

    private fun signUp() {
        val email = _state.value.email
        val password = _state.value.password
        val username = _state.value.username

        when {
            !validateEmailUseCase(email) -> {
                _state.value = _state.value.copy(errorMessage = "Invalid email")
                return
            }

            !validatePasswordUseCase(password) -> {
                _state.value =
                    _state.value.copy(errorMessage = "Password must be at least 6 characters")
                return
            }

            !validateUsernameUseCase(username) -> {
                _state.value =
                    _state.value.copy(errorMessage = "Username must be at least 3 characters")
                return
            }
        }

        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, errorMessage = null)
            val result = signUpUseCase(email, password)
            saveUsernameUseCase(_state.value.username)
            saveUsernameToRemoteUseCase(_state.value.username)

            _state.value = if (result.isSuccess) {
                _state.value.copy(isLoading = false, isSignedUp = true)
            } else {
                _state.value.copy(
                    isLoading = false,
                    errorMessage = result.exceptionOrNull()?.message
                )
            }
        }
    }

    fun continueAsGuest() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, errorMessage = null)
            val result = signInAnonymouslyUseCase()
            if(result.isSuccess){
                val uid = getUserUidUseCase()
                saveUsernameToRemoteUseCase("guest${uid}")
                saveUsernameUseCase("guest${uid}")
                _state.value = _state.value.copy(isLoading = false, isSignedUp = true)

            }else{
                _state.value = _state.value.copy(isLoading = false, errorMessage = result.exceptionOrNull()?.message)

            }
        }
    }
}
