package com.example.wordsapp.history.presentation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wordsapp.history.domain.usecases.GetGameDetailUseCase
import com.example.wordsapp.history.domain.usecases.GetGamesUseCase
import com.example.wordsapp.history.domain.usecases.GetLeaderboardUseCase
import com.example.wordsapp.history.domain.usecases.GetUserUseCase
import com.example.wordsapp.history.presentation.mappers.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getGamesUseCase: GetGamesUseCase,
    private val getGameDetailUseCase: GetGameDetailUseCase,
    private val getLeaderboardUseCase: GetLeaderboardUseCase,
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HistoryState())
    val state = _state.asStateFlow()


    init {
        getGames()
        getLeaderboard()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onEvent(event : HistoryIntent){
        when(event){
            is HistoryIntent.GetGames -> getGames()
            is HistoryIntent.GetGameDetail -> getGameDetail(event.gameId)
            is HistoryIntent.GetLeaderboard -> getLeaderboard()
            is HistoryIntent.GetUser -> getUser(event.userId)
            is HistoryIntent.ChangeWordVisibility -> changeWordVisibility(event.gameId)
            is HistoryIntent.GameCLicked -> getGameDetail(event.gameId)
           is HistoryIntent.ChangeWordVisibilityInDetail -> changeWordVisibilityInDetail()
           is HistoryIntent.ChangeGameDetailVisibility -> changeGameDetailVisibility()
            is HistoryIntent.ChangeTab -> changeTab(event.tab)
        }
    }

    fun changeGameDetailVisibility() {
        _state.update { it.copy(isGameDetailVisible = !it.isGameDetailVisible) }
    }
    fun changeTab(tab : HistoryTabs){
        _state.update { it.copy(currentTab = tab) }
    }

    fun changeWordVisibility(gameId: String) {

        _state.update {
            it.copy(
                gameHistory = it.gameHistory.map {game ->
                    if(game.id == gameId) game.copy(isWordVisible = !game.isWordVisible) else game


                }
            )
        }


    }

    fun changeWordVisibilityInDetail(){
        _state.update {
            it.copy(
                gameDetail = it.gameDetail?.copy(isWordVisible = !it.gameDetail.isWordVisible)
            )
        }

    }




    @RequiresApi(Build.VERSION_CODES.O)
    fun getGames() {
        viewModelScope.launch {
            getGamesUseCase()
                .onStart { _state.value = _state.value.copy(isLoading = true, error = "") }
                .catch { e -> _state.value = _state.value.copy(isLoading = false, error = e.message ?: "Unknown error") }
                .collect { result ->
                    result.fold(
                        onSuccess = { games ->
                            _state.value = _state.value.copy(isLoading = false, gameHistory = games.data.games.map { it.toUi() })
                        },
                        onFailure = { e ->
                            _state.value = _state.value.copy(isLoading = false, error = e.message ?: "Unknown error")
                        }
                    )
                }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getGameDetail(gameId: String) {
        viewModelScope.launch {
            getGameDetailUseCase(gameId)
                .onStart { _state.value = _state.value.copy(isLoading = true, error = "") }
                .catch { e -> _state.value = _state.value.copy(isLoading = false, error = e.message ?: "Unknown error") }
                .collect { result ->
                    result.fold(
                        onSuccess = { game ->
                            _state.value = _state.value.copy(isLoading = false, gameDetail = game.toUi(), isGameDetailVisible = true)
                        },
                        onFailure = { e ->
                            _state.value = _state.value.copy(isLoading = false, error = e.message ?: "Unknown error", isGameDetailVisible = false)
                        }
                    )
                }
        }
    }

    fun getLeaderboard() {
        viewModelScope.launch {
            getLeaderboardUseCase()
                .onStart { _state.value = _state.value.copy(isLoading = true, error = "") }
                .catch { e -> _state.value = _state.value.copy(isLoading = false, error = e.message ?: "Unknown error") }
                .collect { result ->
                    result.fold(
                        onSuccess = { leaderboard ->
                            _state.value = _state.value.copy(isLoading = false, leaderboard = leaderboard.data.leaderboard.map { it.toUi() })
                        },
                        onFailure = { e ->
                            _state.value = _state.value.copy(isLoading = false, error = e.message ?: "Unknown error")
                        }
                    )
                }
        }
    }

    fun getUser(userId: String) {
        Log.e("historyview2", "getUser: ", )

        viewModelScope.launch {
            getUserUseCase(userId)
                .onStart { _state.value = _state.value.copy(isLoading = true, error = "") }
                .catch { e -> _state.value = _state.value.copy(isLoading = false, error = e.message ?: "Unknown error") }
                .collect { result ->
                    Log.e("historyview", "getUser: ${result.getOrNull()}", )

                    result.fold(
                        onSuccess = { user ->
                            _state.value = _state.value.copy(isLoading = false, user = user)
                        },
                        onFailure = { e ->
                            _state.value = _state.value.copy(isLoading = false, error = e.message ?: "Unknown error")
                        }
                    )
                }
        }
    }
}
