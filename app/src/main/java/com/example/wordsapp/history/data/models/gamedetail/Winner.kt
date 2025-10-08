package com.example.wordsapp.history.data.models.gamedetail


import com.google.gson.annotations.SerializedName

data class Winner(
    @SerializedName("eliminated")
    val eliminated: Boolean,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("ready")
    val ready: Boolean,
    @SerializedName("score")
    val score: Int,
    @SerializedName("socketId")
    val socketId: String
)