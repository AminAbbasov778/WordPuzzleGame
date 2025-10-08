package com.example.wordsapp.home.data.module.room


import com.google.gson.annotations.SerializedName

data class Room(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("success")
    val success: Boolean
)