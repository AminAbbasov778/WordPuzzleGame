package com.example.wordsapp.history.data.models.gamedetail


import com.google.gson.annotations.SerializedName

data class Game(
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("difficulty")
    val difficulty: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("language")
    val language: String,
    @SerializedName("players")
    val players: List<Player>,
    @SerializedName("roomId")
    val roomId: String,
    @SerializedName("roomName")
    val roomName: String,
    @SerializedName("winner")
    val winner: Winner,
    @SerializedName("word")
    val word: String
)