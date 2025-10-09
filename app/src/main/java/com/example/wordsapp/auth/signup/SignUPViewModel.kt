package com.example.wordsapp.auth.signup

import androidx.lifecycle.viewModelScope
import com.example.wordsapp.auth.domain.usecases.GetUserUidUseCase
import com.example.wordsapp.auth.domain.usecases.SaveUsernameToRemoteUseCase
import com.example.wordsapp.auth.domain.usecases.SaveUsernameUseCase
import com.example.wordsapp.auth.domain.usecases.SignUpUseCase
import com.example.wordsapp.auth.domain.usecases.ValidateEmailUseCase
import com.example.wordsapp.auth.domain.usecases.ValidatePasswordUseCase
import com.example.wordsapp.auth.domain.usecases.ValidateUsernameUseCase
import com.example.wordsapp.core.presentation.base.BaseViewModel
import com.example.wordsapp.home.domain.usecases.SignInAnonymouslyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
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

    ) : BaseViewModel<SignUpState, SignUpIntent, SignUpNavigation>() {

    override val initialState: SignUpState get()  = SignUpState()
    override fun OnEvent(event: SignUpIntent) {
        when (event) {
            is SignUpIntent.EnterEmail -> updateState {
                it.copy(email = event.email)
            }

            is SignUpIntent.EnterUsername -> updateState {
                it.copy(username = event.username)
            }

            is SignUpIntent.EnterPassword -> updateState {
                it.copy(password = event.password)
            }

            is SignUpIntent.SignUpClicked -> signUp()
            is SignUpIntent.ChangePasswordVisibility -> updateState {
                it.copy(isPasswordVisible = !it.isPasswordVisible)
            }

            SignUpIntent.ContinueAsGuestClicked -> continueAsGuest()
            SignUpIntent.SignInClicked -> gotoSignIn()
        }

    }


    fun gotoSignIn() {
        navigate(SignUpNavigation.SignUpScreenToSignInScreen)

    }


    private fun signUp() {
        val email = state.value.email
        val password = state.value.password
        val username = state.value.username

        when {
            !validateEmailUseCase(email) -> {
                updateState {
                    it.copy(errorMessage = "Invalid email")
                }
                return
            }

            !validatePasswordUseCase(password) -> {
                updateState {
                    it.copy(errorMessage = "Password must be at least 6 characters")
                }
                return
            }

            !validateUsernameUseCase(username) -> {
                updateState {  it.copy(errorMessage = "Username must be at least 3 characters")}

                return
            }
        }

        viewModelScope.launch {
           updateState {  it.copy(isLoading = true, errorMessage = null)}
            val result = signUpUseCase(email, password)
            saveUsernameUseCase(state.value.username)
            saveUsernameToRemoteUseCase(state.value.username)

           if (result.isSuccess) {
               navigate(SignUpNavigation.SignUpScreenToSignInScreen)
               updateState {
                  it.copy(isLoading = false, isSignedUp = true)

               }

            } else {
                updateState {
                    it.copy(
                    isLoading = false,
                    errorMessage = result.exceptionOrNull()?.message
                )
                }
            }
        }
    }

    fun continueAsGuest() {
        viewModelScope.launch {
            updateState {
                it.copy(isLoading = true, errorMessage = null)
            }
            val result = signInAnonymouslyUseCase()
            if (result.isSuccess) {
                val uid = getUserUidUseCase()
                saveUsernameToRemoteUseCase("guest${uid}")
                saveUsernameUseCase("guest_${uid}")
                navigate(SignUpNavigation.SignUpScreenToHomeScreen)

                updateState {
                   it.copy(isLoading = false, isSignedUp = true)
               }

            } else {
               updateState {
                   it.copy(
                       isLoading = false,
                       errorMessage = result.exceptionOrNull()?.message
                   )
               }

            }
        }
    }
}
