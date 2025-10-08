package com.example.wordsapp.history.presentation.history

import com.google.gson.annotations.SerializedName

data class PlayerUi(
                        val eliminated: Boolean,

                        val id: String,
                        val name: String,
                        val score: Int) {
}