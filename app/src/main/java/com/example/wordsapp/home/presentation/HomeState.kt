package com.example.wordsapp.home.presentation

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
) {
}