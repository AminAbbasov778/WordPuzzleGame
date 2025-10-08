package com.example.wordsapp.history.data.models.history


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("games")
    val games: List<Game>
)