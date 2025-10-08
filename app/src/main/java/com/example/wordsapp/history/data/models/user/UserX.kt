package com.example.wordsapp.history.data.models.user


import com.google.gson.annotations.SerializedName

data class UserX(
    @SerializedName("losses")
    val losses: Int?,
    @SerializedName("totalGames")
    val totalGames: Int?,
    @SerializedName("totalScore")
    val totalScore: Int?,
    @SerializedName("userId")
    val userId: String?,
    @SerializedName("username")
    val username: String?,
    @SerializedName("wins")
    val wins: Int?
)