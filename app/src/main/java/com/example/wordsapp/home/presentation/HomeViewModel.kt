package com.example.wordsapp.home.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wordsapp.auth.domain.GetUserUidUseCase
import com.example.wordsapp.core.domain.ConnectSocketUseCase
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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getWaitingRoomsUseCase: GetWaitingRoomsUseCase,
    private val joinRoomsUseCase: JoinRoomsUseCase,
    private val connectSocketUseCase: ConnectSocketUseCase,
    private val updateRoomUseCase: UpdateRoomUseCase,
    private val getUsernameUseCase: GetUsernameUseCase,
    private val signOutUseCase: SignOutUseCase,
    private val getUserUidUseCase: GetUserUidUseCase,
    private val clearUsernameUseCase: ClearUsernameUseCase,
    private val getUsernameFromRemoteUseCase: GetUsernameFromRemoteUseCase
) : ViewModel() {


    val TAG = "HomeViewModel"


    private val _roomsState = MutableStateFlow<HomeState>(HomeState())
    val roomsState: StateFlow<HomeState> = _roomsState.asStateFlow()

    init {
        Log.e(TAG, "init: ", )
        getUsername()

        getWaitingRooms()
        updateRoom()


    }





    fun onEvent(event: HomeIntents) {
        when (event) {
            is HomeIntents.GetWaitingRooms -> getWaitingRooms()
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

                }
            }

            HomeIntents.ConnectSocket ->  connectSocket()
        }

    }




    fun getWaitingRooms() {
        _roomsState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            getWaitingRoomsUseCase().collect { rooms ->
                Log.e(TAG, "getWaitingRooms:${rooms.first().roomId} ", )
                _roomsState.update {
                    it.copy(rooms = rooms.map { it.toUi() }, isLoading = false)
                }
            }
        }


    }

    fun joinRoom(joinRoomUi: JoinRoomUi) {
        joinRoomsUseCase(joinRoomUi.toDomain())
        Log.e(TAG, "joinRoom: count", )

        viewModelScope.launch {
            _roomsState.update {
                it.copy(rooms = it.rooms.map { room ->
                    if (room.roomId == joinRoomUi.roomId) {
                        room.copy(isJoinClicked = true, currentPlayers = room.currentPlayers + 1)
                    } else {
                        room
                    }
                })

            }

        }
    }

    fun connectSocket() {
        viewModelScope.launch {
            Log.e(TAG, "connectSocket: ", )
            connectSocketUseCase()
        }

    }

    fun updateRoom() {
        Log.e(TAG, "updateRoom: ", )
        viewModelScope.launch {
            updateRoomUseCase().collect { roomUpdate ->
                val updateUi = roomUpdate.toUi()

                _roomsState.update { state ->
                    val updatedRooms = state.rooms.map { room ->
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

                    state.copy(rooms = updatedRooms)
                }
            }
        }
    }

    fun getUsername(){
        viewModelScope.launch {
            getUsernameUseCase().collect{username ->
                val userUid =   getUserUidUseCase()

                if(username.isNullOrEmpty()){
                    _roomsState.update {it.copy(username = getUsernameFromRemoteUseCase() ?: "", userUid = userUid)}

                } else{
                    _roomsState.update {it.copy(username = username ?: "", userUid = userUid)}

                }
            }
        }

    }


}