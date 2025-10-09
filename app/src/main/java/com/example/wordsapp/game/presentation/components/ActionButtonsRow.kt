package com.example.wordsapp.game.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.example.wordsapp.game.presentation.intent.GameIntent
import com.example.wordsapp.game.presentation.state.GameState

@Composable
fun ActionButtonsRow(
    state: GameState,
    keyboardController: SoftwareKeyboardController?,
    onEvent: (GameIntent) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(15.dp)
            .fillMaxWidth()
    ) {
        if ((state.isYourTurn && state.isCustomWordVisible) || !state.isGameStarted) {
            ReadyOrCustomWordButton(
                state = state,
                keyboardController = keyboardController,
                onEvent = onEvent,
                modifier = Modifier
                    .weight(1f)
                    .height(45.dp)
            )
        }

        Spacer(
            modifier = if ((state.isYourTurn && state.isCustomWordVisible) || !state.isGameStarted)
                Modifier.width(10.dp)
            else
                Modifier.weight(1f)
        )

        CustomWordToggleButton(
            state = state,
            onEvent = onEvent
        )
    }
}