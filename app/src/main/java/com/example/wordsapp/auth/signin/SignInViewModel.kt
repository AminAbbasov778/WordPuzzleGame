package com.example.wordsapp.auth.signin
import android.annotation.SuppressLint
import androidx.lifecycle.viewModelScope
import com.example.wordsapp.auth.domain.usecases.GetUserUidUseCase
import com.example.wordsapp.auth.domain.usecases.SaveUsernameToRemoteUseCase
import com.example.wordsapp.auth.domain.usecases.SaveUsernameUseCase
import com.example.wordsapp.auth.domain.usecases.SignInUseCase
import com.example.wordsapp.auth.domain.usecases.ValidateEmailUseCase
import com.example.wordsapp.auth.domain.usecases.ValidatePasswordUseCase
import com.example.wordsapp.core.presentation.base.BaseViewModel
import com.example.wordsapp.home.domain.usecases.SignInAnonymouslyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
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
    private val saveUsernameToRemoteUseCase: SaveUsernameToRemoteUseCase,
): BaseViewModel<SignInState, SignInIntent, SignInNavigation>() {
    override val initialState: SignInState get() = SignInState()




    @SuppressLint("SuspiciousIndentation")
    private fun signIn() {
        val email = state.value.email
        val password = state.value.password


        when {
            !validateEmailUseCase(email) -> {
                updateState { state ->
                    state.copy(errorMessage = "Invalid email")
                }
                return
            }
            !validatePasswordUseCase(password) -> {
                updateState { state ->
                    state.copy(errorMessage = "Password must be at least 6 characters")
                }
                return
            }

        }

        updateState { state ->
            state.copy(isLoading = true, errorMessage = null)
        }

        viewModelScope.launch {
            val result = signInUseCase(email, password)
                      anonymouslyUseCase()

            if(result.isSuccess) {
                navigate(SignInNavigation.SignInScreenToHomeScreen)
               updateState { state ->
                    state.copy(isLoading = false, isSignedIn = true)
                }
            } else {
                updateState { state ->
                    state.copy(
                        isLoading = false,
                        error = result.exceptionOrNull()?.message
                    )
                }
            }
        }

    }

    fun continueAsGuest() {
        viewModelScope.launch {
           updateState { state ->
                state.copy(isLoading = true, errorMessage = null)
            }
            val result = anonymouslyUseCase()
            if(result.isSuccess){
                val uid = getUserUidUseCase()
                saveUsernameToRemoteUseCase("guest${uid}")
                saveUsernameUseCase("guest${uid}")
              navigate(SignInNavigation.SignInScreenToHomeScreen)
               updateState { state ->
                    state.copy(isLoading = false, isSignedIn = true)
                }

            }else{
               updateState { state ->
                    state.copy(
                        isLoading = false,
                        error = result.exceptionOrNull()?.message
                    )

            }
        }
    }
    }

    fun gotoSignUp() {
        navigate(SignInNavigation.SignInScreenToSignUpScreen)
    }


    override fun OnEvent(event: SignInIntent) {
        when(event) {
            is SignInIntent.EnterEmail -> updateState { state ->
                state.copy(email = event.email)
            }
            is SignInIntent.EnterPassword -> updateState { state ->
                state.copy(password = event.password)
            }
            is SignInIntent.Submit -> signIn()
            is SignInIntent.ChangePasswordVisibility -> updateState { state ->
                state.copy(isPasswordVisible = !state.isPasswordVisible)
            }
            SignInIntent.ContinueAsGuest -> continueAsGuest()
            SignInIntent.SignUpClicked -> gotoSignUp()

        }
    }


}
