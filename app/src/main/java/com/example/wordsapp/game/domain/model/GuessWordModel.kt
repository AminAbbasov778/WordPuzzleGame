package com.example.wordsapp.game.domain.model

data class GuessWordModel(val roomId: String,
                          val userId: String,
                          val guess: String) {
}