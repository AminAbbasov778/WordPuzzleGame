package com.example.wordsapp.home.data.model.room


import com.google.gson.annotations.SerializedName

data class CreatedAt(
    @SerializedName("_nanoseconds")
    val nanoseconds: Int,
    @SerializedName("_seconds")
    val seconds: Int
)