package com.example.wordsapp.game.domain.model

data class ReadyModel(val roomId: String,
                      val userId: String,
                      val difficulty: String,
                      val language: String) {
}