package com.example.wordsapp.home.presentation

import com.example.wordsapp.home.data.Room

data class HomeState(val rooms : List<RoomUi> = emptyList(), var isStatusGuideVisible : Boolean = false, val userUid : String? = "" ,val isLoading : Boolean = false,val username : String = ""){
}