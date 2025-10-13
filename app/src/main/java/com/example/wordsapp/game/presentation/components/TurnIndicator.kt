package com.example.wordsapp.game.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp


@Composable
fun TurnIndicator(
    isGameStarted: Boolean,
    isYourTurn: Boolean,
    turnPlayerName: String?
) {
    Box(
        modifier = Modifier.padding(horizontal = 80.dp)
            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally)
            .background(
                color = if (isYourTurn) Color(0xFFC40001) else Color(0xFFADAFAF),
                shape = RoundedCornerShape(6.dp)
            )
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = if (!isGameStarted) "Players are waiting..."
            else if (isYourTurn) "Your turn"
            else "${turnPlayerName}'s turn",
            color = Color.White,
            maxLines = 1,
            overflow = if ((turnPlayerName?.length ?: 0) >= 20) TextOverflow.Ellipsis else TextOverflow.Visible
        )
    }
}
