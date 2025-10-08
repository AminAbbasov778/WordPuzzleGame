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
import androidx.compose.ui.draw.blur
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
import com.example.wordsapp.history.presentation.HistoryRouteUi
import com.example.wordsapp.ui.theme.Inter

@Composable
fun GameOverContent(
    state: GameState,
    navController: NavHostController,
    onEvent : (GameIntent) -> Unit
) {
    val isEliminated = state.playerEliminated != null
    val isDraw = state.gameOver?.winner.isNullOrBlank()
    val isWinner = state.gameOver?.winner == state.gameRouteUi.username

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
                navController.popBackStack(Routes.HomeScreen, inclusive = false)
            }
        )
        Spacer(modifier = Modifier.height(8.dp))

        SecondaryButton(
            text = "View Results",
            onClick = {
                navController.navigate(Routes.HistoryScreen(HistoryRouteUi(state.gameRouteUi.userUid)))
            }
        )
    }
}


