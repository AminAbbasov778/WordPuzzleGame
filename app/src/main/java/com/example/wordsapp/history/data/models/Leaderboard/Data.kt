package com.example.wordsapp.history.data.models.Leaderboard


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("leaderboard")
    val leaderboard: List<LeaderboardX>
)