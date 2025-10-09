package com.example.wordsapp.game.presentation.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wordsapp.game.presentation.state.GameState
import com.example.wordsapp.ui.theme.Inter


@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun WordDisplay(state: GameState) {
    val letters: List<String>? = when {
        state.gameUpdateUi != null -> {
            state.gameUpdateUi.discovered
        }
        state.gameStarted != null -> {
            List(state.gameStarted.wordLength) { "_" }
        }
        else -> {
            emptyList()
        }
    }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        val maxWidth = maxWidth
        val letterCount = letters?.size
        val perLetterWidth = maxWidth / (letterCount?.coerceAtLeast(1) ?: 0)
        val fontSize = (perLetterWidth.value * 0.6).sp
        val boxWidth = perLetterWidth * 0.8f

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            letters?.forEach { letter ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = if (letter == "_") " " else letter.uppercase(),
                        style = Inter.copy(
                            fontSize = fontSize,
                            color = Color.White
                        )
                    )
                    Box(
                        modifier = Modifier
                            .height(1.dp)
                            .width(boxWidth)
                            .background(color = Color(0xFFACAEB1))
                    )
                }
            }
        }
    }
}