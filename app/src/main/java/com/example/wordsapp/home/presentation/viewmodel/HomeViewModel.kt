package com.example.wordsapp.home.presentation.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewModelScope
import com.example.wordsapp.auth.domain.usecases.GetUserUidUseCase
import com.example.wordsapp.core.presentation.base.BaseViewModel
import com.example.wordsapp.core.presentation.enums.Action
import com.example.wordsapp.core.presentation.enums.Difficulty
import com.example.wordsapp.core.presentation.enums.Language
import com.example.wordsapp.core.presentation.enums.Status
import com.example.wordsapp.game.domain.usecase.GetHomeStateUseCase
import com.example.wordsapp.game.domain.usecase.PlayerJoinedFlowUseCase
import com.example.wordsapp.history.presentation.model.HistoryRouteUi
import com.example.wordsapp.home.domain.usecases.ClearUsernameUseCase
import com.example.wordsapp.home.domain.usecases.ConnectSocketUseCase
import com.example.wordsapp.home.domain.usecases.GetRoomsUseCase
import com.example.wordsapp.home.domain.usecases.GetUsernameFromRemoteUseCase
import com.example.wordsapp.home.domain.usecases.GetUsernameUseCase
import com.example.wordsapp.home.domain.usecases.IsConnectedUseCase
import com.example.wordsapp.home.domain.usecases.JoinRoomsUseCase
import com.example.wordsapp.home.domain.usecases.SignOutUseCase
import com.example.wordsapp.home.domain.usecases.UpdateRoomUseCase
import com.example.wordsapp.home.presentation.intent.HomeIntents
import com.example.wordsapp.home.presentation.mapper.toDomain
import com.example.wordsapp.home.presentation.mapper.toUi
import com.example.wordsapp.home.presentation.model.GameRouteUi
import com.example.wordsapp.home.presentation.model.JoinRoomUi
import com.example.wordsapp.home.presentation.model.StatusUi
import com.example.wordsapp.home.presentation.navigation.HomeNavigation
import com.example.wordsapp.home.presentation.state.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val joinRoomsUseCase: JoinRoomsUseCase,
    private val connectSocketUseCase: ConnectSocketUseCase,
    private val updateRoomUseCase: UpdateRoomUseCase,
    private val getUsernameUseCase: GetUsernameUseCase,
    private val signOutUseCase: SignOutUseCase,
    private val getUserUidUseCase: GetUserUidUseCase,
    private val clearUsernameUseCase: ClearUsernameUseCase,
    private val getHomeStateUseCase: GetHomeStateUseCase,
    private val getRoomsUseCase: GetRoomsUseCase,
    private val playerJoinedFlowUseCase: PlayerJoinedFlowUseCase,
    private val getUsernameFromRemoteUseCase: GetUsernameFromRemoteUseCase,
    private val isConnectedUseCase: IsConnectedUseCase,
)  : BaseViewModel<HomeState, HomeIntents, HomeNavigation>() {

    override val initialState: HomeState get() = HomeState()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun OnEvent(event: HomeIntents) {
            when (event) {
                is HomeIntents.GetWaitingRooms -> getRooms()
                HomeIntents.ChangeStatusGuideVisibility -> updateState {
                    it.copy(
                        isStatusGuideVisible = !it.isStatusGuideVisible
                    )
                }

                is HomeIntents.JoinRoom -> joinRoom(event.joinRoomUi)
                HomeIntents.SignOut -> {
                    signOutUseCase()
                    viewModelScope.launch {
                        clearUsernameUseCase()
                        navigate(HomeNavigation.HomeScreenToSignInScreen)
                        updateState {
                            it.copy(isSignOut = true)
                        }




                    }
                }

                HomeIntents.ConnectSocket -> connectSocket()
                is HomeIntents.ChangeCurrentTab -> changeCurrentTab(event.status)
                HomeIntents.GoToHistoryScreen -> goToHistoryScreen()
            }


    }

    fun goToHistoryScreen() {
        navigate(HomeNavigation.HomeScreenToHistoryScreen(HistoryRouteUi(state.value.userUid ?: "",state.value.username)))
    }


    val TAG = "jdn"


   private var roomsJob: Job? = null


    init {
        getStatus()
        connectSocket()
        getUsername()
        getRooms()


    }




    @RequiresApi(Build.VERSION_CODES.O)
    fun getRooms() {
        roomsJob?.cancel()
       roomsJob =  viewModelScope.launch(Dispatchers.IO) {
            getRoomsUseCase(if (state.value.selectedTab == Status.WAITING) 0 else if (state.value.selectedTab == Status.GAME) 1 else 2)
                .onStart {updateState { state ->
                    state.copy(isLoading = true)
                } }
                .catch { e -> updateState { state ->
                    state.copy(isLoading = false)
                } }
                .collect { result ->
                    result.fold(
                        onSuccess = { rooms ->
                           updateState {it.copy(
                               isLoading = false,
                               filteredRooms = rooms.data.rooms.map { it.toUi() }) }
                        },
                        onFailure = { e ->
                            updateState { state ->
                                state.copy(isLoading = false)
                            }
                        }
                    )
                }


        }
    }

    fun changeCurrentTab(status: Status) {

        updateState { state ->
            state.copy(
                selectedTab = status,
                status = state.status.map {
                    if (it.status == status) it.copy(isSelected = true) else it.copy(isSelected = false)
                },

                )}


        getRooms()
    }




    fun joinRoom(joinRoomUi: JoinRoomUi) {
        joinRoomsUseCase(joinRoomUi.toDomain())

        viewModelScope.launch {
            updateState {
                it.copy(allRooms = it.allRooms.map { room ->
                    if (room.roomId == joinRoomUi.roomId) {
                        navigate(HomeNavigation.HomeScreenToGameScreen(GameRouteUi(
                            roomId = room.roomId,
                            roomName = room.roomName,
                            status = if(room.status == Status.WAITING) "waiting" else if(room.status == Status.GAME) "game" else "full",
                            maxPlayers = room.maxPlayers,
                            username = state.value.username,
                            userUid = state.value.userUid ?: ""
                            ,
                            currentPlayers = room.currentPlayers,
                            difficulty = if(room.difficulty == Difficulty.EASY) "easy" else if(room.difficulty == Difficulty.MEDIUM) "medium" else "hard",
                            language = if(room.language == Language.EN) "en" else if(room.language == Language.AZ) "az" else "tr",
                        )))
                        room.copy(
                            isJoinClicked = true,
                            currentPlayers = room.currentPlayers + 1
                        )
                    } else {
                        room
                    }
                }
                )

            }

        }
    }

    fun connectSocket() {
        if (isConnectedUseCase()) return
        viewModelScope.launch {
            connectSocketUseCase()
            getHomeState()
        }

    }

    fun updateRoom() {
        Log.e(TAG, "updateRoom: ")
        viewModelScope.launch {
            updateRoomUseCase().collect { roomUpdate ->
                val updateUi = roomUpdate.toUi()

                updateState { state ->
                    val updatedRooms = state.allRooms.map { room ->
                        Log.e(TAG, "updateRoom:iswoked ${roomUpdate.toString()}")

                        if (room.roomId == updateUi.roomId) {
                            when (updateUi.action) {
                                Action.JOIN -> room.copy(
                                    currentPlayers = room.currentPlayers + 1
                                )

                                Action.LEAVE -> room.copy(
                                    currentPlayers = (room.currentPlayers - 1).coerceAtLeast(0)
                                )
                            }
                        } else {
                            room
                        }
                    }

                    state.copy(allRooms = updatedRooms)
                }
            }
        }
    }

    fun getUsername() {
        viewModelScope.launch {
            getUsernameUseCase().collect { username ->
                val userUid = getUserUidUseCase()

                if (username.isNullOrEmpty()) {
                  val result =  getUsernameFromRemoteUseCase()
                  if(result.isSuccess)
                  {
                      updateState {
                          it.copy(
                              username = result.getOrNull() ?: "", userUid = userUid
                          )
                      }
                  }

                } else {
                    updateState { it.copy(username = username , userUid = userUid) }

                }
            }
        }

    }

    fun getHomeState() {
        viewModelScope.launch {
            getHomeStateUseCase().collect { homeState ->
                if (state.value.allRooms.any { it.roomId == homeState.roomId }) {
                    updateState { roomState ->
                        roomState.copy(
                            allRooms = roomState.allRooms.map {
                                if (it.roomId == homeState.roomId)
                                    it.copy(currentPlayers = homeState.players.size)
                                else it
                            }
                        )
                    }
                }
            }
            updateRoom()
        }

    }

    fun getStatus() {
        val status = listOf<StatusUi>(
            StatusUi("Waiting", true, Status.WAITING),
            StatusUi("Game", false, Status.GAME),
            StatusUi("Full", false, Status.FULL)

        )
        updateState {
            it.copy(status = status)
        }
    }

}