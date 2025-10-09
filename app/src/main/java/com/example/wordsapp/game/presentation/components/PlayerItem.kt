package com.example.wordsapp.game.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wordsapp.game.presentation.model.PlayerJoinedUi
import com.example.wordsapp.ui.theme.Inter

@Composable
fun PlayerItem(player: PlayerJoinedUi) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.weight(0.5f)) {
            Text(
                text = player.username,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = Inter.copy(
                    color = Color(0xFFCFCFCF).copy(0.7f),
                    fontSize = 12.sp,
                    letterSpacing = 0.8.sp
                ),
                modifier = Modifier
                    .padding(start = 10.dp)
                    .align(Alignment.End)
            )
        }

        Spacer(modifier = Modifier.width(11.dp))

        Column(modifier = Modifier.weight(0.5f)) {
            Text(
                text = if (player.isReady) "ready" else "not ready",
                style = Inter.copy(
                    color = if (player.isReady)
                        Color(0xFF3EB481).copy(0.7f)
                    else
                        Color(0xFFC40100).copy(0.7f),
                    fontSize = 12.sp,
                    letterSpacing = 0.8.sp
                ),
                modifier = Modifier.align(Alignment.Start)
            )
        }
    }
}