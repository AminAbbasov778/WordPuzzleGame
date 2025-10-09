package com.example.wordsapp.home.presentation.state

import com.example.wordsapp.core.presentation.base.UIState
import com.example.wordsapp.core.presentation.enums.Status
import com.example.wordsapp.home.presentation.model.RoomUi
import com.example.wordsapp.home.presentation.model.StatusUi

data class HomeState(
    val status: List<StatusUi> = emptyList(),
    val selectedTab: Status = Status.WAITING,
    val filteredRooms: List<RoomUi> = emptyList(),
    val allRooms: List<RoomUi> = emptyList(),
    var isStatusGuideVisible: Boolean = false,
    val userUid: String? = "",
    val isLoading: Boolean = false,
    val username: String = "",
    val isSignOut : Boolean = false
) : UIState{
}