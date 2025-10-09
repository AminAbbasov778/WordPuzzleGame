package com.example.wordsapp.home.presentation.intent

import com.example.wordsapp.core.presentation.base.UIIntent
import com.example.wordsapp.core.presentation.enums.Status
import com.example.wordsapp.home.presentation.model.JoinRoomUi

sealed class HomeIntents : UIIntent {
    object GetWaitingRooms : HomeIntents()
    object ChangeStatusGuideVisibility : HomeIntents()
    data class JoinRoom(val joinRoomUi: JoinRoomUi) : HomeIntents()
     object SignOut : HomeIntents()
     object ConnectSocket : HomeIntents()
     object GoToHistoryScreen : HomeIntents()
    data class ChangeCurrentTab(val status: Status) : HomeIntents()
}