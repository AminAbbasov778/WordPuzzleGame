package com.example.wordsapp.core.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


abstract class BaseViewModel<State : UIState, Intent : UIIntent, Nav : Navigation> : ViewModel() {

    abstract val initialState: State

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<State> = _state.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    val _navigation: Channel<Nav> = Channel()
    val navigation = _navigation.receiveAsFlow()

    fun updateState(update: (State) -> State) {
        _state.value = update(_state.value)
    }

    abstract fun OnEvent(event: Intent)

    fun navigate(navigation: Nav) {
        _navigation.trySend(navigation)
    }

    fun setLoading(value: Boolean) {
        _isLoading.value = value
    }

    fun <T> execute(
        call: suspend () -> T,
        onSuccess: (T) -> Unit,
        onFail: ((Throwable) -> Unit)? = null
    ) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                handleRequest(call, onSuccess, onFail)
            }
        }
    }

    private suspend fun <T> handleRequest(
        call: suspend () -> T,
        onSuccess: (T) -> Unit,
        onFail: ((Throwable) -> Unit)? = null
    ) {
        try {
            val result = call()
            onSuccess(result)
        } catch (e: Throwable) {
            onFail?.invoke(e)
        }
    }

}