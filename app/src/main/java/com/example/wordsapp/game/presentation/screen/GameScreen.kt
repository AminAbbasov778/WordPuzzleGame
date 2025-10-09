package com.example.wordsapp.game.presentation.screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.wordsapp.core.navigation.Routes
import com.example.wordsapp.game.presentation.components.BottomSection
import com.example.wordsapp.game.presentation.components.GameBottomSheet
import com.example.wordsapp.game.presentation.components.GameContent
import com.example.wordsapp.game.presentation.components.TopBar
import com.example.wordsapp.game.presentation.intent.GameIntent
import com.example.wordsapp.game.presentation.state.GameState
import com.example.wordsapp.home.presentation.model.GameRouteUi

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun GameScreen(
    state: GameState,
    arguments: GameRouteUi,
    onEvent: (GameIntent) -> Unit,
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val keyboardController = LocalSoftwareKeyboardController.current
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    LaunchedEffect(Unit) {
        onEvent(GameIntent.UpdateGame(arguments))
    }

    LaunchedEffect(state.isLeft) {
        if (state.isLeft) {
            onEvent(GameIntent.ClearState)
            onEvent(GameIntent.GoToHomeScreen)
        }
    }

    BackHandler(enabled = true) {
        onEvent(GameIntent.GoBack)
    }

    Scaffold {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color(0xFF222830))
                    .blur(if (state.gameOver != null || state.isBack) 4.dp else 0.dp)
                    .systemBarsPadding()
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                TopBar(
                    state = state,
                    onBackClick = { onEvent(GameIntent.GoBack) },
                    onPlayersClick = { onEvent(GameIntent.ReadyPlayerSheetVisibility) }
                )

                Spacer(modifier = Modifier.height(17.dp))

                GameContent(
                    state = state,
                    screenHeight = screenHeight,
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                )

                BottomSection(
                    state = state,
                    keyboardController = keyboardController,
                    onEvent = onEvent
                )
            }

            if (state.gameOver != null || state.isReadyPlayerSheetOpen || state.isGameOver || state.isBack) {
                GameBottomSheet(
                    state = state,
                    sheetState = sheetState,
                    onEvent = onEvent
                )
            }
        }
    }
}