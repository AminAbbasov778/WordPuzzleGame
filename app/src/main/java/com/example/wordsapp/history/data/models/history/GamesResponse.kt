package com.example.wordsapp.history.data.models.history


import com.google.gson.annotations.SerializedName

data class GamesResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("success")
    val success: Boolean
)