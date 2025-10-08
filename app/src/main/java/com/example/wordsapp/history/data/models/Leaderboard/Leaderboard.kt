package com.example.wordsapp.history.data.models.Leaderboard


import com.google.gson.annotations.SerializedName

data class Leaderboard(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("success")
    val success: Boolean
)