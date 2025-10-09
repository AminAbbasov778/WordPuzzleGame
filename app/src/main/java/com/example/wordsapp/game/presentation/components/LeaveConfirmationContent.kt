package com.example.wordsapp.game.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wordsapp.game.presentation.intent.GameIntent
import com.example.wordsapp.game.presentation.model.LeaveRoomUi
import com.example.wordsapp.game.presentation.state.GameState
import com.example.wordsapp.ui.theme.Inter
import kotlinx.coroutines.async

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeaveConfirmationContent(
    state: GameState,
    onEvent: (GameIntent) -> Unit,sheetState: SheetState
) {
    val scope = rememberCoroutineScope()


    Text(
        text = "Are you sure you want to leave?",
        style = Inter.copy(
            color = Color(0xFFCFCFCF),
            fontSize = 16.sp,
            letterSpacing = 0.7.sp
        ),
        textAlign = TextAlign.Center,
        modifier = Modifier
            .padding(horizontal = 18.dp, vertical = 4.dp)
            .fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(28.dp))

    Text(
        text = "Leaving will eliminate you from this game. Do you still want to exit?",
        style = Inter.copy(
            color = Color(0xFFCFCFCF).copy(0.8f),
            fontSize = 15.sp,
            letterSpacing = 0.7.sp
        ),
        textAlign = TextAlign.Center,
        modifier = Modifier
            .padding(horizontal = 18.dp, vertical = 4.dp)
            .fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(55.dp))

    Row(
        modifier = Modifier
            .padding(horizontal = 18.dp)
            .fillMaxWidth()
    ) {
        SecondaryButton(
            text = "Stay",
            onClick = { onEvent(GameIntent.GoBack) },
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(10.dp))

        PrimaryButton(
            text = "Exit game",
            onClick = {
                scope.async { sheetState.hide() }
                onEvent(

                    GameIntent.LeaveRoom(

                        LeaveRoomUi(
                            state.gameRouteUi.roomId,
                            state.gameRouteUi.userUid
                        )
                    )
                )
            },
            modifier = Modifier.weight(1f)
        )
    }
}