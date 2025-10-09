package com.example.wordsapp.game.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wordsapp.game.presentation.intent.GameIntent
import com.example.wordsapp.game.presentation.model.GuessWordUi
import com.example.wordsapp.game.presentation.model.ReadyUi
import com.example.wordsapp.game.presentation.model.UnreadyUpdateUi
import com.example.wordsapp.game.presentation.state.GameState
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
                            UnreadyUpdateUi(
                                state.gameRouteUi.roomId,
                                state.gameRouteUi.userUid
                            )
                        )
                    )
                } else if (state.isCancelled && !state.isGameStarted) {
                    onEvent(
                        GameIntent.Ready(
                            ReadyUi(
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
                                    GuessWordUi(
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