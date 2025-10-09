package com.example.wordsapp.history.presentation.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wordsapp.core.presentation.base.BaseViewModel
import com.example.wordsapp.history.domain.usecases.GetGameDetailUseCase
import com.example.wordsapp.history.domain.usecases.GetGamesUseCase
import com.example.wordsapp.history.domain.usecases.GetLeaderboardUseCase
import com.example.wordsapp.history.domain.usecases.GetUserUseCase
import com.example.wordsapp.history.presentation.enums.HistoryTabs
import com.example.wordsapp.history.presentation.intent.HistoryIntent
import com.example.wordsapp.history.presentation.mappers.toUi
import com.example.wordsapp.history.presentation.navigation.HistoryNavigation
import com.example.wordsapp.history.presentation.state.HistoryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
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
    private val getUserUseCase: GetUserUseCase,

) : BaseViewModel<HistoryState, HistoryIntent, HistoryNavigation>() {


    override val initialState: HistoryState get() = HistoryState()


    init {
        getLeaderboard()
    }


    override fun OnEvent(event: HistoryIntent) {
        when(event){
            is HistoryIntent.GetGames -> getGames(event.userId)
            is HistoryIntent.GetGameDetail -> getGameDetail(event.gameId)
            is HistoryIntent.GetLeaderboard -> getLeaderboard()
            is HistoryIntent.GetUser -> getUser(event.userId)
            is HistoryIntent.ChangeWordVisibility -> changeWordVisibility(event.gameId)
            is HistoryIntent.GameCLicked -> getGameDetail(event.gameId)
            is HistoryIntent.ChangeWordVisibilityInDetail -> changeWordVisibilityInDetail()
            is HistoryIntent.ChangeGameDetailVisibility -> changeGameDetailVisibility()
            is HistoryIntent.ChangeTab -> changeTab(event.tab)
            HistoryIntent.GoToHome -> goToHome()

        }
    }

    fun goToHome() {
        navigate(HistoryNavigation.HistoryScreenToHomeScreen)
    }



    fun changeGameDetailVisibility() {
        updateState { it.copy(isGameDetailVisible = !it.isGameDetailVisible) }
    }
    fun changeTab(tab : HistoryTabs){
        updateState { it.copy(currentTab = tab) }
    }

    fun changeWordVisibility(gameId: String) {

        updateState {
            it.copy(
                gameHistory = it.gameHistory.map {game ->
                    if(game.id == gameId) game.copy(isWordVisible = !game.isWordVisible) else game


                }
            )
        }


    }

    fun changeWordVisibilityInDetail(){
        updateState {
            it.copy(
                gameDetail = it.gameDetail?.copy(isWordVisible = !it.gameDetail.isWordVisible)
            )
        }

    }




    @RequiresApi(Build.VERSION_CODES.O)
    fun getGames(userId: String) {
        viewModelScope.launch {
            getGamesUseCase()
                .onStart { updateState { it.copy(isLoading = true, error = "") }}
                .catch { e -> updateState { it.copy(isLoading = false, error = e.message ?: "Unknown error") }}
                .collect { result ->
                    result.fold(
                        onSuccess = { games ->
                            updateState { it.copy(isLoading = false, gameHistory = games.data.games.filter { it.players.any { player -> player.id == userId } }.map { it.toUi() })}
                        },
                        onFailure = { e ->
                            updateState{ it.copy(isLoading = false, error = e.message ?: "Unknown error")}
                        }
                    )
                }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getGameDetail(gameId: String) {
        viewModelScope.launch {
            getGameDetailUseCase(gameId)
                .onStart { updateState {it.copy(isLoading = true, error = "") }}
                .catch { e ->updateState {it.copy(isLoading = false, error = e.message ?: "Unknown error") }}
                .collect { result ->
                    result.fold(
                        onSuccess = { game ->
                            updateState { it.copy(isLoading = false, gameDetail = game.toUi(), isGameDetailVisible = true)}
                        },
                        onFailure = { e ->
                            updateState {it.copy(isLoading = false, error = e.message ?: "Unknown error", isGameDetailVisible = false)}
                        }
                    )
                }
        }
    }

    fun getLeaderboard() {
        viewModelScope.launch {
            getLeaderboardUseCase()
                .onStart { updateState{ it.copy(isLoading = true, error = "") }}
                .catch { e ->updateState {it.copy(isLoading = false, error = e.message ?: "Unknown error") }}
                .collect { result ->
                    result.fold(
                        onSuccess = { leaderboard ->
                            updateState { it.copy(isLoading = false, leaderboard = leaderboard.data.leaderboard.map { it.toUi() })}
                        },
                        onFailure = { e ->
                            updateState { it.copy(isLoading = false, error = e.message ?: "Unknown error")}
                        }
                    )
                }
        }
    }

    fun getUser(userId: String) {
        Log.e("historyview2", "getUser: ", )

        viewModelScope.launch {
            getUserUseCase(userId)
                .onStart { updateState {it.copy(isLoading = true, error = "") }}
                .catch { e -> updateState { it.copy(isLoading = false, error = e.message ?: "Unknown error") }}
                .collect { result ->
                    Log.e("historyview", "getUser: ${result.getOrNull()}", )

                    result.fold(
                        onSuccess = { user ->
                            updateState  { it.copy(isLoading = false, user = user)}
                        },
                        onFailure = { e ->
                            updateState{ it.copy(isLoading = false, error = e.message ?: "Unknown error")}
                        }
                    )
                }
        }
    }

}