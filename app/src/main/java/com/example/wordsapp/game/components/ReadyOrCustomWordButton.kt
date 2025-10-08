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
fun ReadyOrCustomWordButton(
    state: GameState,
    keyboardController: SoftwareKeyboardController?,
    onEvent: (GameIntent) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                color = if (!state.isReady) Color(0xFFC50000)
                else if (state.isGameStarted) Color(0xFF382A2A)
                else Color(0xFFC50000).copy(alpha = 0.5f),
                shape = RoundedCornerShape(15.dp)
            )
            .border(
                width = 1.dp,
                color = Color(0xFFC50000).copy(alpha = 0.5f),
                shape = RoundedCornerShape(15.dp)
            )
            .clickable {
                if (state.isReady && !state.isGameStarted) {
                    onEvent(
                        GameIntent.UnreadyUpdate(
                            UnreadyUpdate(
                                state.gameRouteUi.roomId,
                                state.gameRouteUi.userUid
                            )
                        )
                    )
                } else if (state.isCancelled && !state.isGameStarted) {
                    onEvent(
                        GameIntent.Ready(
                            Ready(
                                state.gameRouteUi.roomId,
                                state.gameRouteUi.userUid,
                                state.gameRouteUi.difficulty,
                                state.gameRouteUi.language
                            )
                        )
                    )
                }
            },
        contentAlignment = Alignment.Center
    ) {
        when {
            state.isReady && !state.isGameStarted -> {
                Text("Cancel", style = Inter.copy(color = Color.White, fontSize = 18.sp))
            }
            state.isCancelled && !state.isGameStarted -> {
                Text("Ready", style = Inter.copy(color = Color.White, fontSize = 18.sp))
            }
            state.isCustomWordVisible -> {
                CustomWordTextField(
                    value = state.customWord,
                    onValueChange = { onEvent(GameIntent.InputText(it)) },
                    onDone = {
                        if (state.customWord.isNotBlank()) {
                            onEvent(
                                GameIntent.GuessWord(
                                    GuessWord(
                                        state.gameRouteUi.roomId,
                                        state.gameRouteUi.userUid,
                                        state.customWord
                                    )
                                )
                            )
                            keyboardController?.hide()
                        }
                    }
                )
            }
        }
    }
}