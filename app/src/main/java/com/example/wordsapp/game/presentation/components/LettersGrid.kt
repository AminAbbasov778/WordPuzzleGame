package com.example.wordsapp.game.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.wordsapp.game.presentation.intent.GameIntent
import com.example.wordsapp.game.presentation.model.GuessLetterUi
import com.example.wordsapp.game.presentation.state.GameState

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LettersGrid(
    state: GameState,
    onEvent: (GameIntent) -> Unit
) {
    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 11.dp),
        horizontalArrangement = Arrangement.Center,
        verticalArrangement = Arrangement.spacedBy(9.dp)
    ) {
        state.letters.forEach { letter ->
            LetterBox(
                letter = letter,
                isYourTurn = state.isYourTurn,
                onClick = {
                    onEvent(
                        GameIntent.GuessLetter(
                            GuessLetterUi(
                                state.gameRouteUi.roomId,
                                state.gameRouteUi.userUid,
                                letter.char
                            )
                        )
                    )
                }
            )
        }
    }
}
