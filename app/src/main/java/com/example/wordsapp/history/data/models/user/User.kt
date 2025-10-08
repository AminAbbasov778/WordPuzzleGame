package com.example.wordsapp.history.data.models.user


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("success")
    val success: Boolean
)