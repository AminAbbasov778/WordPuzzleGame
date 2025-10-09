package com.example.wordsapp.game.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.wordsapp.R
import com.example.wordsapp.game.presentation.intent.GameIntent
import com.example.wordsapp.game.presentation.state.GameState

@Composable
fun CustomWordToggleButton(
    state: GameState,
    onEvent: (GameIntent) -> Unit
) {
    Box(
        modifier = Modifier
            .size(45.dp)
            .background(
                color = Color(0xFFC50000).copy(alpha = 0.5f),
                shape = RoundedCornerShape(30.dp)
            )
            .border(
                width = 1.dp,
                color = Color(0xFFC50000).copy(alpha = 0.5f),
                shape = RoundedCornerShape(30.dp)
            )
            .clickable {
                if (state.isGameStarted && state.guessingCounts > 2) {
                    onEvent(GameIntent.CustomWordVisibility)
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = if (state.isCustomWordVisible)
                painterResource(R.drawable.ic_remove)
            else
                painterResource(R.drawable.ic_question),
            contentDescription = "question",
            tint = if (((state.isCancelled || state.isReady) && !state.isGameStarted) || state.guessingCounts <= 2)
                Color(0xFFFFFFFF).copy(alpha = 0.5f)
            else
                Color.White
        )
    }
}
