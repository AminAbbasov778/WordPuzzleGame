package com.example.wordsapp.game.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.wordsapp.core.navigation.Routes
import com.example.wordsapp.game.presentation.intent.GameIntent
import com.example.wordsapp.game.presentation.state.GameState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameBottomSheet(
    state: GameState,
    sheetState: SheetState,
    onEvent: (GameIntent) -> Unit
) {
    ModalBottomSheet(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .navigationBarsPadding()
            .padding(bottom = 20.dp)
            .fillMaxWidth(),
        onDismissRequest = {
            if (!state.isBack) {
                if (state.gameOver != null) {
                    onEvent(GameIntent.GoToHomeScreen)
                } else {
                    onEvent(GameIntent.ReadyPlayerSheetVisibility)
                }
            } else {
                onEvent(GameIntent.GoBack)
            }
        },
        sheetState = sheetState,
        containerColor = Color(0xFF2E3740),
        shape = RoundedCornerShape(16.dp),
        tonalElevation = 8.dp,
        dragHandle = {}
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0xFF2E3740)
                )
        ) {
            Box(
                modifier = Modifier
                    .padding(top = 12.dp)
                    .height(4.dp)
                    .fillMaxWidth(0.1f)
                    .background(
                        color = Color(0xFFACAFB1).copy(alpha = 0.5f),
                        shape = RoundedCornerShape(4.dp)
                    )
                    .align(Alignment.TopCenter)
            )

            Column(
                modifier = Modifier
                    .padding(top = 17.dp)
                    .fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                when {
                    state.isBack -> LeaveConfirmationContent(state, onEvent,sheetState)
                    state.isReadyPlayerSheetOpen -> PlayersListContent(state)
                    else -> GameOverContent(state, sheetState,onEvent = {onEvent(it)})
                }

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}