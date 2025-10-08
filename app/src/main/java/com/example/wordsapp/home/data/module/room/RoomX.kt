package com.example.wordsapp.home.data.module.room


import com.google.gson.annotations.SerializedName

data class RoomX(
    @SerializedName("createdAt")
    val createdAt: CreatedAt,
    @SerializedName("difficulty")
    val difficulty: String,
    @SerializedName("hasWinner")
    val hasWinner: Boolean,
    @SerializedName("language")
    val language: String,
    @SerializedName("maxPlayers")
    val maxPlayers: Int,
    @SerializedName("playerCount")
    val playerCount: Int,
    @SerializedName("roomId")
    val roomId: String,
    @SerializedName("roomName")
    val roomName: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("winner")
    val winner: Boolean?,
    @SerializedName("word")
    val word: Boolean?,
    @SerializedName("wrongGuesses")
    val wrongGuesses: Int
)