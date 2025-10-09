package com.example.wordsapp.game.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.example.wordsapp.game.presentation.intent.GameIntent
import com.example.wordsapp.game.presentation.state.GameState

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BottomSection(
    state: GameState,
    keyboardController: SoftwareKeyboardController?,
    onEvent: (GameIntent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color(0xFF1F262D))
    ) {
        ActionButtonsRow(
            state = state,
            keyboardController = keyboardController,
            onEvent = onEvent
        )

        Spacer(modifier = Modifier.height(13.dp))

        LettersGrid(
            state = state,
            onEvent = onEvent
        )

        Spacer(modifier = Modifier.height(25.dp))
    }
}
