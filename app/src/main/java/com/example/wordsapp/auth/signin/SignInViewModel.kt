package com.example.wordsapp.auth.signin
import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wordsapp.auth.domain.GetUserUidUseCase
import com.example.wordsapp.auth.domain.SaveUsernameToRemoteUseCase
import com.example.wordsapp.auth.domain.SaveUsernameUseCase
import com.example.wordsapp.auth.domain.SignInUseCase
import com.example.wordsapp.auth.domain.ValidateEmailUseCase
import com.example.wordsapp.auth.domain.ValidatePasswordUseCase
import com.example.wordsapp.home.domain.usecases.SignInAnonymouslyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val anonymouslyUseCase: SignInAnonymouslyUseCase,
    private val getUserUidUseCase: GetUserUidUseCase,
    private val saveUsernameUseCase : SaveUsernameUseCase,
    private val saveUsernameToRemoteUseCase: SaveUsernameToRemoteUseCase
): ViewModel() {

    private val _state = MutableStateFlow(SignInState())
    val state: StateFlow<SignInState> = _state

    fun onEvent(intent: SignInIntent) {
        when(intent) {
            is SignInIntent.EnterEmail -> _state.value = _state.value.copy(email = intent.email)
            is SignInIntent.EnterPassword -> _state.value = _state.value.copy(password = intent.password)
            is SignInIntent.Submit -> signIn()
            is SignInIntent.ChangePasswordVisibility -> _state.value = _state.value.copy(isPasswordVisible = !_state.value.isPasswordVisible)
            SignInIntent.ContinueAsGuest -> continueAsGuest()
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun signIn() {
        val email = _state.value.email
        val password = _state.value.password


        when {
            !validateEmailUseCase(email) -> {
                _state.value = _state.value.copy(errorMessage = "Invalid email")
                return
            }
            !validatePasswordUseCase(password) -> {
                _state.value = _state.value.copy(errorMessage = "Password must be at least 6 characters")
                return
            }

        }

        _state.value = _state.value.copy(isLoading = true, error = null)

        viewModelScope.launch {
            val result = signInUseCase(email, password)
                      anonymouslyUseCase()

            if(result.isSuccess) {
                _state.value = _state.value.copy(isLoading = false, isSignedIn = true)
            } else {
                _state.value = _state.value.copy(isLoading = false, error = result.exceptionOrNull()?.message)
            }
        }

    }

    fun continueAsGuest() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, errorMessage = null)
            val result = anonymouslyUseCase()
            if(result.isSuccess){
                val uid = getUserUidUseCase()
                saveUsernameToRemoteUseCase("guest${uid}")
                saveUsernameUseCase("guest${uid}")
                _state.value = _state.value.copy(isLoading = false, isSignedIn = true)

            }else{
                _state.value = _state.value.copy(isLoading = false, error = result.exceptionOrNull()?.message)

            }
        }
    }
}
