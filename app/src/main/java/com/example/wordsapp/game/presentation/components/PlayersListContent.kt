package com.example.wordsapp.game.presentation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wordsapp.game.presentation.state.GameState
import com.example.wordsapp.ui.theme.Inter

@Composable
fun PlayersListContent(state: GameState) {
    Text(
        text = "Players",
        style = Inter.copy(
            color = Color(0xFFCFCFCF),
            fontSize = 14.sp,
            letterSpacing = 0.7.sp
        ),
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 4.dp)
            .fillMaxWidth(),
        textAlign = TextAlign.Center
    )

    Text(
        text = "${state.joinedList.size}/${state.gameRouteUi.maxPlayers}",
        style = Inter.copy(
            color = Color(0xFFCFCFCF).copy(0.7f),
            fontSize = 10.sp,
            letterSpacing = 0.7.sp
        ),
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 4.dp)
            .fillMaxWidth(),
        textAlign = TextAlign.Center
    )

    Spacer(modifier = Modifier.height(12.dp))

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(state.joinedList) { player ->
            PlayerItem(player = player)
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}