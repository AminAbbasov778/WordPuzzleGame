package com.example.wordsapp.home.data.module.room


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("rooms")
    val rooms: List<RoomX>
)