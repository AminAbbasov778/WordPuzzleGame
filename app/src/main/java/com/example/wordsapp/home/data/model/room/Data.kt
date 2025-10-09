package com.example.wordsapp.home.data.model.room


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("rooms")
    val rooms: List<RoomX>
)