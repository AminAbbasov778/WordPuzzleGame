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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameBottomSheet(
    state: GameState,
    sheetState: SheetState,
    navController: NavHostController,
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
                    navController.popBackStack(Routes.HomeScreen, inclusive = false)
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
                    state.isBack -> LeaveConfirmationContent(state, onEvent)
                    state.isReadyPlayerSheetOpen -> PlayersListContent(state)
                    else -> GameOverContent(state, navController,onEvent = {onEvent(it)})
                }

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}