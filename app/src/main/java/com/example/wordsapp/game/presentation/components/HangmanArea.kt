package com.example.wordsapp.game.presentation.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.wordsapp.R

@Composable
fun HangmanArea(
    isGameStarted: Boolean,
    totalWrongGuesses: Int,
    screenHeight: Dp
) {
    Box(
        modifier = Modifier
            .height(screenHeight / 4)
            .padding(horizontal = 30.dp)
            .fillMaxWidth()
            .background(
                color = Color(0xFF2E3740),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        if (!isGameStarted) {
            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.ripple_animation))
            val progress by animateLottieCompositionAsState(
                composition,
                iterations = LottieConstants.IterateForever
            )

            LottieAnimation(
                composition = composition,
                progress = { progress },
                modifier = Modifier
                    .padding(horizontal = 60.dp)
                    .height(screenHeight / 5)
                    .fillMaxWidth()
            )
        } else {
            Log.e("SocketView", "SocketView$totalWrongGuesses ")
            HangmanCanvas(totalWrongGuesses)
        }
    }
}
