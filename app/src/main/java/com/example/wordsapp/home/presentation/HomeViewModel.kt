package com.example.wordsapp.home.presentation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wordsapp.auth.domain.GetUserUidUseCase
import com.example.wordsapp.core.domain.ConnectSocketUseCase
import com.example.wordsapp.history.presentation.mappers.toUi
import com.example.wordsapp.home.domain.usecases.ClearUsernameUseCase
import com.example.wordsapp.home.domain.usecases.GetUsernameFromRemoteUseCase
import com.example.wordsapp.home.domain.usecases.GetUsernameUseCase
import com.example.wordsapp.home.domain.usecases.GetWaitingRoomsUseCase
import com.example.wordsapp.home.domain.usecases.JoinRoomsUseCase
import com.example.wordsapp.home.domain.usecases.SignOutUseCase
import com.example.wordsapp.home.domain.usecases.UpdateRoomUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
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
    private val getRoomStateUseCase: com.example.wordsapp.game.domain.GetRoomStateUseCase,
    private val getRoomsUseCase: com.example.wordsapp.home.domain.usecases.GetRoomsUseCase,
    private val getUsernameFromRemoteUseCase: GetUsernameFromRemoteUseCase,
    private val isConnectedUseCase: com.example.wordsapp.core.domain.IsConnectedUseCase,
) : ViewModel() {


    val TAG = "HomeViewModel"


    private val _roomsState = MutableStateFlow<HomeState>(HomeState())
    val roomsState: StateFlow<HomeState> = _roomsState.asStateFlow()

    init {
        getStatus()
        connectSocket()
        getUsername()
        getRooms()


    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun onEvent(event: HomeIntents) {
        when (event) {
            is HomeIntents.GetWaitingRooms -> getRooms()
            HomeIntents.ChangeStatusGuideVisibility -> _roomsState.update {
                it.copy(
                    isStatusGuideVisible = !it.isStatusGuideVisible
                )
            }

            is HomeIntents.JoinRoom -> joinRoom(event.joinRoomUi)
            HomeIntents.SignOut -> {
                signOutUseCase()
                viewModelScope.launch {
                    clearUsernameUseCase()
                        _roomsState.update {
                            it.copy(isSignOut = true)
                        }




                }
            }

            HomeIntents.ConnectSocket -> connectSocket()
            is HomeIntents.ChangeCurrentTab -> changeCurrentTab(event.status)
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getRooms() {
        viewModelScope.launch {
            getRoomsUseCase(if (_roomsState.value.selectedTab == Status.WAITING) 0 else if (_roomsState.value.selectedTab == Status.GAME) 1 else 2)
                .onStart { _roomsState.value = _roomsState.value.copy(isLoading = true) }
                .catch { e -> _roomsState.value = _roomsState.value.copy(isLoading = false) }
                .collect { result ->
                    result.fold(
                        onSuccess = { rooms ->
                            _roomsState.value = _roomsState.value.copy(
                                isLoading = false,
                                filteredRooms = rooms.data.rooms.map { it.toUi() })
                        },
                        onFailure = { e ->
                            _roomsState.value = _roomsState.value.copy(isLoading = false)
                        }
                    )
                }


        }
    }

    fun changeCurrentTab(status: Status) {

        _roomsState.update { state ->
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
        Log.e(TAG, "joinRoom: count")

        viewModelScope.launch {
            _roomsState.update {
                it.copy(allRooms = it.allRooms.map { room ->
                    if (room.roomId == joinRoomUi.roomId) {
                        room.copy(
                            isJoinClicked = true,
                            currentPlayers = room.currentPlayers + 1
                        )
                    } else {
                        room
                    }
                })

            }

        }
    }

    fun connectSocket() {
        if (isConnectedUseCase()) return
        viewModelScope.launch {
            Log.e(TAG, "connectSocket: ")
            connectSocketUseCase()
            updateRoom()
            getHomeState()
        }

    }

    fun updateRoom() {
        Log.e(TAG, "updateRoom: ")
        viewModelScope.launch {
            updateRoomUseCase().collect { roomUpdate ->
                val updateUi = roomUpdate.toUi()

                _roomsState.update { state ->
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
                    _roomsState.update {
                        it.copy(
                            username = getUsernameFromRemoteUseCase() ?: "", userUid = userUid
                        )
                    }

                } else {
                    _roomsState.update { it.copy(username = username ?: "", userUid = userUid) }

                }
            }
        }

    }

    fun getHomeState() {
        viewModelScope.launch {
            getRoomStateUseCase().collect { homeState ->
                if (roomsState.value.allRooms.any { it.roomId == homeState.roomId }) {
                    _roomsState.update { roomState ->
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
        }
    }

    fun getStatus() {
        val status = listOf<StatusUi>(
            StatusUi("Waiting", true, Status.WAITING),
            StatusUi("Game", false, Status.GAME),
            StatusUi("Full", false, Status.FULL)

        )
        _roomsState.update {
            it.copy(status = status)
        }
    }

}


