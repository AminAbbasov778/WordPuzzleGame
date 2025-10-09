package com.example.wordsapp.game.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.wordsapp.game.presentation.state.GameState


@Composable
fun TopBar(
    state: GameState,
    onBackClick: () -> Unit,
    onPlayersClick: () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        LeaveButton(
            isBack = state.isBack,
            onClick = onBackClick,
            modifier = Modifier
                .padding(start = 15.dp)
                .weight(1f)
        )

        GameInfoSection(
            state = state,
            onPlayersClick = onPlayersClick,
            modifier = Modifier.padding(end = 15.dp)
        )
    }
}