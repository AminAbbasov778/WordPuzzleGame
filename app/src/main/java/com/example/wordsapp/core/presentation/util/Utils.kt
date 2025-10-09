package com.example.wordsapp.core.presentation.util

import com.example.wordsapp.core.presentation.enums.Action
import com.example.wordsapp.core.presentation.enums.Difficulty
import com.example.wordsapp.core.presentation.enums.Language
import com.example.wordsapp.core.presentation.enums.Status


fun String.toLanguage(): Language =
    when (this.lowercase()) {
        "en" -> Language.EN
        "az" -> Language.AZ
        "aze" -> Language.AZ
        "tr" -> Language.TR
        else -> throw IllegalArgumentException("Unknown language: $this")
    }

fun String.toStatus(): Status =
    when (this.lowercase()) {
        "waiting" -> Status.WAITING
        "game" -> Status.GAME
        "full" -> Status.FULL
        else -> Status.WAITING
    }

fun String.toDifficulty(): Difficulty =
    when (this.lowercase()) {
        "easy" -> Difficulty.EASY
        "medium" -> Difficulty.MEDIUM
        "hard" -> Difficulty.HARD
        else -> throw IllegalArgumentException("Unknown difficulty: $this")
    }

fun String.toAction(): Action =
    when (this.lowercase()) {
        "join" -> Action.JOIN
        "leave" -> Action.LEAVE
        else -> throw IllegalArgumentException("Unknown action: $this")
    }


fun String.capitalizeFirstLetter(): String {
    return this.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase() else it.toString()
    }
}
