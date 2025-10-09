package com.example.wordsapp.game.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.wordsapp.game.presentation.state.GameState


@Composable
fun GameContent(
    state: GameState,
    screenHeight: Dp,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color(0xFF1F262D))
            .systemBarsPadding()
    ) {
        Spacer(modifier = Modifier.height(15.dp))

        TurnIndicator(
            isGameStarted = state.isGameStarted,
            isYourTurn = state.isYourTurn,
            turnPlayerName = state.turn?.name
        )

        Spacer(modifier = Modifier.height(5.dp))

        TurnProgressBar(
            turnTimer = state.turnTimer,
            maxTime = 30f
        )

        Spacer(modifier = Modifier.height(25.dp))

        HangmanArea(
            isGameStarted = state.isGameStarted,
            totalWrongGuesses = state.totalWrongGuesses,
            screenHeight = screenHeight
        )

        Spacer(modifier = Modifier.height(70.dp))

        WordDisplay(state = state)

        if (state.isGuessesVisible) {
            Spacer(modifier = Modifier.height(20.dp))
            GuessedLetterInfo(
                guessedBy = state.gameUpdateUi?.guessedBy,
                guessedLetter = state.gameUpdateUi?.guessedLetter,
                isCorrect = state.gameUpdateUi?.correct
            )
        }
    }
}