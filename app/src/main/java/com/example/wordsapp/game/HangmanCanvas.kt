package com.example.wordsapp.game

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke

@Composable
fun HangmanCanvas(wrongGuesses: Int) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val baseWidth = 329f
        val baseHeight = 214f

        val scaleX = size.width / baseWidth
        val scaleY = size.height / baseHeight

        val offsetY = -10f
        val horizontalOffset = (size.width - baseWidth * scaleX) / 2f


        fun px(x: Float, y: Float) = Offset(x * scaleX + horizontalOffset, (y + offsetY) * scaleY)

        if (wrongGuesses >= 1) {
            drawLine(
                color = Color.White,
                start = px(82f, 214f),
                end = px(147f, 214f),
                strokeWidth = 3f * scaleX,
                cap = StrokeCap.Round
            )
        }

        if (wrongGuesses >= 2) {
            drawLine(
                color = Color.White,
                start = px(115f, 214f),
                end = px(115f, 44f), // 214 - 170
                strokeWidth = 3f * scaleX,
                cap = StrokeCap.Round
            )
            // diaqonal dəstək (rotation effekti)
            drawLine(
                color = Color.White,
                start = px(115f, 104f),   // 214 - 110
                end = px(165f, 44f),      // sağ yuxarıya
                strokeWidth = 3f * scaleX
            )
        }

        // --- 3: üst çubuq (uzunluq ~150, qalınlıq 3)
        if (wrongGuesses >= 3) {
            drawLine(
                color = Color.White,
                start = px(115f, 43f),
                end = px(265f, 43f),
                strokeWidth = 3f * scaleX
            )
        }

        // --- 4: ip (uzunluq 27, qalınlıq 3)
        if (wrongGuesses >= 4) {
            drawLine(
                color = Color.White,
                start = px(265f, 43f),
                end = px(265f, 70f),
                strokeWidth = 3f * scaleX
            )
        }

        // --- 5: baş (eni 19, uzunluğu 20, border 3)
        if (wrongGuesses >= 5) {
            drawOval(
                color = Color.White,
                topLeft = px(255f, 70f),
                size = Size(19f * scaleX, 20f * scaleY),
                style = Stroke(width = 3f * scaleX)
            )
        }

        // --- 6: bədən (uzunluq 27, qalınlıq 2)
        if (wrongGuesses >= 6) {
            drawLine(
                color = Color.White,
                start = px(265f, 90f),
                end = px(265f, 120f),
                strokeWidth = 2f * scaleX
            )
        }

        // --- 7: sol qol (uzunluq 18, qalınlıq 1.5)
        if (wrongGuesses >= 7) {
            drawLine(
                color = Color.White,
                start = px(265f, 95f),
                end = px(247f, 113f),
                strokeWidth = 1.5f * scaleX
            )
        }

        // --- 8: sağ qol
        if (wrongGuesses >= 8) {
            drawLine(
                color = Color.White,
                start = px(265f, 95f),
                end = px(283f, 113f),
                strokeWidth = 1.5f * scaleX
            )
        }

        // --- 9: sol ayaq (uzunluq 25, qalınlıq 2)
        if (wrongGuesses >= 9) {
            drawLine(
                color = Color.White,
                start = px(265f, 117f),
                end = px(250f, 142f),
                strokeWidth = 2f * scaleX
            )
        }

        // --- 10: sağ ayaq
        if (wrongGuesses >= 10) {
            drawLine(
                color = Color.White,
                start = px(265f, 117f),
                end = px(280f, 142f),
                strokeWidth = 2f * scaleX
            )
        }
    }
}
