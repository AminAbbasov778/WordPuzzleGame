package com.example.wordsapp.game.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.wordsapp.game.presentation.model.LetterUi

@Composable
fun LetterBox(
    letter: LetterUi,
    isYourTurn: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(end = 4.dp)
            .width(34.dp)
            .height(41.dp)
            .background(
                color = when {
                    letter.isCorrect == null -> Color(0xFF2E3740)
                    letter.isCorrect -> Color(0xFF305B31)
                    else -> Color(0xFFC50000)
                },
                shape = RoundedCornerShape(10.dp)
            )
            .then(
                if (!letter.isSelected && isYourTurn)
                    Modifier.clickable { onClick() }
                else
                    Modifier
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(text = letter.char, color = Color(0xFFC4C4C4))
    }
}
