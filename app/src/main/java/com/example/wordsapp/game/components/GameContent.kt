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
fun GameContent(
    state: GameState,
    screenHeight: Dp,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color(0xFF1F262D))
            .systemBarsPadding()
    ) {
        Spacer(modifier = Modifier.height(15.dp))

        TurnIndicator(
            isGameStarted = state.isGameStarted,
            isYourTurn = state.isYourTurn,
            turnPlayerName = state.turn?.name
        )

        Spacer(modifier = Modifier.height(5.dp))

        TurnProgressBar(
            turnTimer = state.turnTimer,
            maxTime = 30f
        )

        Spacer(modifier = Modifier.height(25.dp))

        HangmanArea(
            isGameStarted = state.isGameStarted,
            totalWrongGuesses = state.totalWrongGuesses,
            screenHeight = screenHeight
        )

        Spacer(modifier = Modifier.height(70.dp))

        WordDisplay(state = state)

        if (state.isGuessesVisible) {
            Spacer(modifier = Modifier.height(20.dp))
            GuessedLetterInfo(
                guessedBy = state.gameUpdateUi?.guessedBy,
                guessedLetter = state.gameUpdateUi?.guessedLetter,
                isCorrect = state.gameUpdateUi?.correct
            )
        }
    }
}