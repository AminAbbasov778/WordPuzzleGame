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
import com.example.wordsapp.ui.theme.Inter

@Composable
fun LeaveConfirmationContent(
    state: GameState,
    onEvent: (GameIntent) -> Unit
) {
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
                onEvent(
                    GameIntent.LeaveRoom(
                        LeaveRoom(
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