package com.example.wordsapp.history.data.models.history


import com.google.gson.annotations.SerializedName

data class Player(
    @SerializedName("eliminated")
    val eliminated: Boolean,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("score")
    val score: Int
)