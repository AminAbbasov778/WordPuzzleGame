package com.example.wordsapp.history.data.models.gamedetail


import com.google.gson.annotations.SerializedName

data class GameDetail(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("success")
    val success: Boolean
)