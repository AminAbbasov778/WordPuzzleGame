package com.example.wordsapp.home.presentation

sealed class HomeIntents {
    object GetWaitingRooms : HomeIntents()
    object ChangeStatusGuideVisibility : HomeIntents()
    data class JoinRoom(val joinRoomUi: JoinRoomUi) : HomeIntents()
     object SignOut : HomeIntents()
     object ConnectSocket : HomeIntents()
    data class ChangeCurrentTab(val status: Status) : HomeIntents()
}

