package com.example.wordsapp.game.presentation.model

data class LetterUi(
    val char: String,
    val isSelected: Boolean = false,
    val isCorrect: Boolean? = null
)