package com.example.wordsapp.history.data.models.Leaderboard


import com.google.gson.annotations.SerializedName

data class LeaderboardX(
    @SerializedName("rank")
    val rank: Int,
    @SerializedName("totalScore")
    val totalScore: Int,
    @SerializedName("userId")
    val userId: String,
    @SerializedName("username")
    val username: String
)