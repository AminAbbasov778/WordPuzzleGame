package com.example.wordsapp.game.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.wordsapp.ui.theme.Inter

@Composable
fun GuessedLetterInfo(
    guessedBy: String?,
    guessedLetter: String?,
    isCorrect: Boolean?
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "$guessedBy guessed : ",
            style = Inter.copy(
                color = Color(0xFFACAEB1),
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
        )
        Text(
            text = guessedLetter?.uppercase() ?: "",
            style = Inter.copy(
                color = if (isCorrect == true) Color(0xFF305B31) else Color(0xFFC50000),
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
        )
    }
}