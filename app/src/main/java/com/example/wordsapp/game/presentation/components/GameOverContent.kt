package com.example.wordsapp.game.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.wordsapp.R
import com.example.wordsapp.core.navigation.Routes
import com.example.wordsapp.game.presentation.intent.GameIntent
import com.example.wordsapp.game.presentation.state.GameState
import com.example.wordsapp.history.presentation.model.HistoryRouteUi
import com.example.wordsapp.ui.theme.Inter
import kotlinx.coroutines.async

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameOverContent(
    state: GameState,sheetState: SheetState,
    onEvent : (GameIntent) -> Unit
) {
    val isEliminated = state.playerEliminated != null
    val isDraw = state.gameOver?.winner.isNullOrBlank()
    val isWinner = state.gameOver?.winner == state.gameRouteUi.username
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxWidth()){
        Text(
            text = when {
                isEliminated -> "You Are Eliminated! ❌"
                isDraw -> "It's a Draw! \uD83E\uDD1D"
                isWinner -> "Congratulations! \uD83C\uDF89"
                else -> "Round Over! \uD83C\uDFC6"
            },
            style = Inter.copy(
                color = Color(0xFFCFCFCF),
                fontSize = 16.sp,
                letterSpacing = 0.7.sp
            ),
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 4.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(36.dp))

        Text(
            text = when {
                isEliminated -> "Your guess was incorrect. You are out of this round."
                isDraw -> "No one could guess the word. Better luck next time!"
                isWinner -> "You guessed the word correctly and won this round!"
                else -> "${state.gameOver?.winner} guessed the word correctly and won this round."
            },
            style = Inter.copy(color = Color(0xFFCFCFCF), fontSize = 15.sp),
            modifier = Modifier.padding(horizontal = 59.dp),
            textAlign = TextAlign.Center
        )

        when {
            isDraw -> {
                Spacer(modifier = Modifier.height(16.dp))


                if (state.isWordVisible) {
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier) {
                        Text(
                            text = state.gameOver?.word ?: "",
                            style = Inter.copy(
                                color = Color.White.copy(if (state.isWordVisible) 0f else 0.8f)
                            ),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.SemiBold
                        )


                        Spacer(modifier = Modifier.width(9.dp))
                        Icon(
                            painter = painterResource(if (!state.isWordVisible) R.drawable.ic_invisible_small else R.drawable.ic_visible_small),
                            contentDescription = null,
                            tint = Color.Unspecified,
                            modifier = Modifier
                                .align(
                                    Alignment.CenterVertically
                                )
                                .clickable {
                                    onEvent(GameIntent.ChangeWordVisibility)

                                })
                    }
                }





                Spacer(modifier = Modifier.height(16.dp))

            }

            else -> Spacer(modifier = Modifier.height(55.dp))


        }

        PrimaryButton(
            text = "Exit",
            onClick = {
                scope.async { sheetState.hide()}

               onEvent(GameIntent.GoToHomeScreen)
            }
        )
        Spacer(modifier = Modifier.height(8.dp))

        SecondaryButton(
            text = "View Results",
            onClick = {
                scope.async { sheetState.hide()}
  onEvent(  GameIntent.GoToHistoryScreen(
      HistoryRouteUi(state.gameRouteUi.userUid,state.gameRouteUi.username)))
            }
        )
    }
}


