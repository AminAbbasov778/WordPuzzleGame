package com.example.wordsapp.game.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wordsapp.R
import com.example.wordsapp.game.presentation.state.GameState
import com.example.wordsapp.ui.theme.Inter


@Composable
fun GameInfoSection(
    state: GameState,
    onPlayersClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        InfoItem(
            icon = R.drawable.ic_difficulty,
            text = state.gameRouteUi.difficulty,
            onClick = onPlayersClick
        )
        Spacer(modifier = Modifier.width(8.dp))

        InfoItem(
            icon = R.drawable.iconoir_language,
            text = state.gameRouteUi.language,
            onClick = onPlayersClick
        )
        Spacer(modifier = Modifier.width(8.dp))

        InfoItem(
            icon = R.drawable.ic_stash_user_group,
            text = "${state.joinedList.size}/${state.gameRouteUi.maxPlayers}",
            onClick = onPlayersClick
        )
        Spacer(modifier = Modifier.width(8.dp))

        Icon(
            painter = painterResource(R.drawable.ic_watch),
            tint = Color(0xFFCFCFCF),
            contentDescription = "watch"
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = "${state.turnTimer}s",
            style = Inter.copy(fontSize = 16.sp, color = Color(0xFFC4C4C4))
        )
    }
}