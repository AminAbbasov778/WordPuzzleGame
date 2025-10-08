package com.example.wordsapp.game.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.*
import com.example.wordsapp.R
import com.example.wordsapp.core.data.models.*
import com.example.wordsapp.core.navigation.Routes
import com.example.wordsapp.game.GameIntent
import com.example.wordsapp.game.GameState
import com.example.wordsapp.game.HangmanCanvas
import com.example.wordsapp.ui.theme.Inter


@Composable
fun WordDisplay(state: GameState) {
    val letters: List<String> = when {
        state.gameUpdateUi != null -> {
            Log.e("Game", "GameScreen: gameUpdateUi")
            state.gameUpdateUi.discovered
        }
        state.gameStarted != null -> {
            Log.e("Game", "GameScreen: gameStarted")
            List(state.gameStarted.wordLength) { "_" }
        }
        else -> {
            Log.e("Game", "GameScreen: emptylist ")
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
        val letterCount = letters.size
        val perLetterWidth = maxWidth / (letterCount.coerceAtLeast(1))
        val fontSize = (perLetterWidth.value * 0.6).sp
        val boxWidth = perLetterWidth * 0.8f

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            letters.forEach { letter ->
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