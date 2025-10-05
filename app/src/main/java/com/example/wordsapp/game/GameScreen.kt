package com.example.wordsapp.game

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.wordsapp.R
import com.example.wordsapp.core.data.models.GuessLetter
import com.example.wordsapp.core.data.models.GuessWord
import com.example.wordsapp.core.data.models.LeaveRoom
import com.example.wordsapp.core.data.models.Ready
import com.example.wordsapp.core.data.models.UnreadyUpdate
import com.example.wordsapp.core.navigation.Routes
import com.example.wordsapp.home.presentation.GameRouteUi
import com.example.wordsapp.ui.theme.Inter

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedBoxWithConstraintsScope")
@Composable
fun GameScreen(
    navController: NavHostController,
    state: GameState,
    arguments: GameRouteUi,
    onEvent: (GameIntent) -> Unit,
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val lazyGridState = rememberLazyGridState()
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    var text by remember { mutableStateOf("") }
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.ripple_animation))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever
    )
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val keyboardController = LocalSoftwareKeyboardController.current
    val progressLinear = remember(state.turnTimer) {
        state.turnTimer.toFloat() / 30f
    }

    LaunchedEffect(Unit) {
        Log.e("GameScreenGiris", "GameScreenGiris sayi: ${arguments.toString()}")

        onEvent(GameIntent.UpdateGame(arguments))

    }
    LaunchedEffect(state.isLeft) {
        if (state.isLeft) {
            onEvent(GameIntent.ClearState)
            Log.e("GameScreen", "GameScreen: ${state.isLeft.toString()}")
            navController.popBackStack(Routes.HomeScreen, inclusive = false)


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

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {


                    Spacer(modifier = Modifier.height(16.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Row(
                            modifier = Modifier
                                .padding(start = 15.dp)
                                .weight(1f)
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() }) {

                                    if (!state.isBack) {
                                        onEvent(GameIntent.GoBack)

                                    }

                                },
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_back),
                                tint = Color.Unspecified,
                                contentDescription = "back",
                                modifier = Modifier
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "Leave",
                                style = Inter.copy(fontSize = 16.sp, color = Color(0xFFC4C4C4))
                            )

                        }

                        Row(
                            modifier = Modifier.padding(end = 15.dp),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(modifier = Modifier.clickable {
                                onEvent(GameIntent.ReadyPlayerSheetVisibility)

                            }, verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_difficulty),
                                    tint = Color.Unspecified,
                                    contentDescription = "people",
                                    modifier = Modifier
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = state.gameRouteUi.difficulty,
                                    style = Inter.copy(fontSize = 16.sp, color = Color(0xFFC4C4C4))
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))


                            Row(modifier = Modifier.clickable {
                                onEvent(GameIntent.ReadyPlayerSheetVisibility)

                            }, verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    painter = painterResource(R.drawable.iconoir_language),
                                    tint = Color.Unspecified,
                                    contentDescription = "people",
                                    modifier = Modifier
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = state.gameRouteUi.language,
                                    style = Inter.copy(fontSize = 16.sp, color = Color(0xFFC4C4C4))
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))




                            Row(modifier = Modifier.clickable {
                                onEvent(GameIntent.ReadyPlayerSheetVisibility)

                            }, verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_stash_user_group),
                                    tint = Color.Unspecified,
                                    contentDescription = "people",
                                    modifier = Modifier
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "${state.joinedList.size}/${state.gameRouteUi.maxPlayers}",
                                    style = Inter.copy(fontSize = 16.sp, color = Color(0xFFC4C4C4))
                                )
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            Icon(
                                painter = painterResource(R.drawable.ic_watch),
                                tint = Color(0xFFCFCFCF),
                                contentDescription = "watch",
                                modifier = Modifier
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${state.turnTimer}s",
                                style = Inter.copy(fontSize = 16.sp, color = Color(0xFFC4C4C4))
                            )

                        }

                    }


                    Spacer(modifier = Modifier.height(17.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = Color(0xFF1F262D))
                            .systemBarsPadding()
                    ) {
                        Spacer(modifier = Modifier.height(15.dp))

                        Box(
                            modifier = Modifier
                                .background(
                                    color = if (state.isYourTurn) Color(0xFFC40001) else Color(
                                        0xFFADAFAF
                                    ),
                                    shape = RoundedCornerShape(6.dp)
                                )
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .align(
                                    Alignment.CenterHorizontally
                                )
                        ) {
                            Text(
                                text = if (!state.isGameStarted) "Players are waiting..." else if (state.isYourTurn) "Your turn" else "${state.turn?.name}'s turn",
                                color = Color.White
                            )
                        }
                        Spacer(modifier = Modifier.height(5.dp))



                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.6f)
                                .height(3.dp)
                                .clip(RoundedCornerShape(50.dp))
                                .background(Color(0xFFD9D9D9))
                                .align(Alignment.CenterHorizontally)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth(progressLinear.coerceIn(0f, 1f))
                                    .align(Alignment.CenterEnd) // sağdan sola dolsun
                                    .background(Color(0xFF949699)) // boz xətt
                            )
                        }



                        Spacer(modifier = Modifier.height(25.dp))


                        Box(
                            modifier = Modifier
                                .height(screenHeight / 4)
                                .padding(horizontal = 30.dp)
                                .fillMaxWidth()
                                .background(
                                    color = Color(0xFF2E3740),
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .align(
                                    Alignment.CenterHorizontally
                                ), contentAlignment = Alignment.Center
                        ) {

                            if (!state.isGameStarted) {

                                LottieAnimation(
                                    composition = composition,
                                    progress = { progress },
                                    modifier = Modifier
                                        .padding(horizontal = 60.dp)
                                        .height(screenHeight / 5)
                                        .fillMaxWidth()
                                        .align(Alignment.Center)
                                )


                            } else {
                                Log.e("SocketView", "SocketView${state.totalWrongGuesses} ")
                                HangmanCanvas(state.totalWrongGuesses)
                            }


                        }

                        Spacer(modifier = Modifier.height(70.dp))

                        val letters: List<String> = if (state.gameUpdateUi != null) {
                            Log.e("Game", "GameScreen: gameUpdateUi")
                            state.gameUpdateUi.discovered

                        } else {
                            if (state.gameStarted != null) {
                                Log.e("Game", "GameScreen: gameStarted")

                                List(state.gameStarted.wordLength) { "_" }

                            } else {
                                Log.e("Game", "GameScreen: emptylist ")

                                emptyList()
                            }
                        }


                        BoxWithConstraints(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            val maxWidth = maxWidth
                            val letterCount = letters.size

                            // Hər hərfin sahəsi üçün en hesablayırıq
                            val perLetterWidth = maxWidth / (letterCount.coerceAtLeast(1))
                            val fontSize = (perLetterWidth.value * 0.6).sp // 60% sahəni font üçün
                            val boxWidth = perLetterWidth * 0.8f           // Alt xəttin eni

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                letters.forEach { letter ->
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            text = if (letter == "_") " " else letter.uppercase(),
                                            style = Inter.copy(
                                                fontSize = fontSize,
                                                color = Color.White
                                            )
                                        )
                                        Box(
                                            modifier = Modifier
                                                .height(1.dp)
                                                .width(boxWidth)
                                                .background(color = Color(0xFFACAEB1))
                                        )
                                    }
                                }
                            }
                        }


                        if (state.isGuessesVisible) {
                            Spacer(modifier = Modifier.height(20.dp))
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            ) {
                                Text(
                                    text = "${state.gameUpdateUi?.guessedBy} guessed : ",
                                    style = Inter.copy(
                                        color = Color(0xFFACAEB1),
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium
                                    ),
                                )

                                Text(
                                    text = state.gameUpdateUi?.guessedLetter?.uppercase() ?: "",
                                    style = Inter.copy(
                                        color = if (state.gameUpdateUi?.correct == true) Color(
                                            0xFF305B31
                                        ) else Color(0xFFC50000),
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium
                                    ),
                                )
                            }

                        }


                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color(0xFF1F262D))
                ) {


                    Row(
                        modifier = Modifier
                            .padding(15.dp)
                            .fillMaxWidth()
                    ) {
                        if ((state.isYourTurn && state.isCustomWordVisible) || !state.isGameStarted) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(45.dp)
                                    .background(
                                        color = if (!state.isReady) Color(0xFFC50000) else if (state.isGameStarted) Color(
                                            0xFF382A2A
                                        ) else Color(
                                            0xFFC50000
                                        ).copy(alpha = 0.5f),
                                        shape = RoundedCornerShape(15.dp)
                                    )
                                    .border(
                                        width = 1.dp,
                                        color = Color(0xFFC50000).copy(alpha = 0.5f),
                                        shape = RoundedCornerShape(15.dp)
                                    )
                                    .clickable {
                                        if (state.isReady && !state.isGameStarted) onEvent(
                                            GameIntent.UnreadyUpdate(
                                                UnreadyUpdate(
                                                    state.gameRouteUi.roomId,
                                                    state.gameRouteUi.userUid
                                                )
                                            )
                                        )
                                        else if (state.isCancelled && !state.isGameStarted) onEvent(
                                            GameIntent.Ready(
                                                Ready(
                                                    state.gameRouteUi.roomId,
                                                    state.gameRouteUi.userUid,
                                                    state.gameRouteUi.difficulty,
                                                    state.gameRouteUi.language
                                                )
                                            )
                                        )


                                    },
                                contentAlignment = Alignment.Center
                            ) {


                                if (state.isReady && !state.isGameStarted) {
                                    Text(
                                        "Cancel",
                                        style = Inter.copy(color = Color.White, fontSize = 18.sp)
                                    )
                                } else if (state.isCancelled && !state.isGameStarted) {
                                    Text(
                                        "Ready",
                                        style = Inter.copy(color = Color.White, fontSize = 18.sp)
                                    )
                                } else if (state.isCustomWordVisible) {
                                    BasicTextField(
                                        value = state.customWord,
                                        onValueChange = { onEvent(GameIntent.InputText(it)) },
                                        textStyle = Inter.copy(
                                            color = Color.White,
                                            fontSize = 12.sp
                                        ),
                                        cursorBrush = Brush.verticalGradient(
                                            colors = listOf(
                                                Color.White,
                                                Color.White
                                            )

                                        ), keyboardOptions = KeyboardOptions.Default.copy(
                                            imeAction = ImeAction.Done
                                        ), keyboardActions = KeyboardActions(
                                            onDone = {
                                                if (state.customWord.isNotBlank()) {
                                                    onEvent(
                                                        GameIntent.GuessWord(
                                                            GuessWord(
                                                                state.gameRouteUi.roomId,
                                                                state.gameRouteUi.userUid,
                                                                state.customWord
                                                            )
                                                        )
                                                    )
                                                    keyboardController?.hide()
                                                }
                                            }
                                        ),
                                        maxLines = 1,
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(horizontal = 14.dp)
                                    ) { innerTextField ->
                                        Box(
                                            modifier = Modifier.fillMaxWidth(),
                                            contentAlignment = Alignment.CenterStart
                                        ) {
                                            if (state.customWord.isEmpty()) Text(
                                                "Custom word here",
                                                style = Inter.copy(
                                                    color = Color.White,
                                                    fontSize = 12.sp
                                                )
                                            )
                                            innerTextField()
                                        }
                                    }
                                }

                            }
                        }

                        Spacer(
                            modifier = if ((state.isYourTurn && state.isCustomWordVisible) || !state.isGameStarted) Modifier.width(
                                10.dp
                            ) else Modifier.weight(1f)
                        )
                        Box(
                            modifier = Modifier
                                .size(45.dp)
                                .background(
                                    color = Color(0xFFC50000).copy(alpha = 0.5f),
                                    shape = RoundedCornerShape(30.dp)
                                )
                                .border(
                                    width = 1.dp,
                                    color = Color(0xFFC50000).copy(alpha = 0.5f),
                                    shape = RoundedCornerShape(30.dp)
                                )
                                .clickable {
                                    if (state.isGameStarted && state.guessingCounts > 2) {
                                        onEvent(GameIntent.CustomWordVisibility)
                                    }
                                }, contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = if (state.isCustomWordVisible) painterResource(R.drawable.ic_remove) else painterResource(
                                    R.drawable.ic_question
                                ),
                                contentDescription = "question",
                                tint = if(((state.isCancelled || state.isReady) && !state.isGameStarted) || state.guessingCounts <= 2) Color(0xFFFFFFFF).copy(alpha = 0.5f)  else Color.White,
                                modifier = Modifier.align(
                                    Alignment.Center
                                )
                            )
                        }
                    }





                    Spacer(modifier = Modifier.height(13.dp))
                    FlowRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 15.dp, end = 11.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalArrangement = Arrangement.spacedBy(9.dp),

                        content = {

                            state.letters.forEach { letter ->


                                Box(
                                    modifier = Modifier
                                        .padding(end = 4.dp)
                                        .width(34.dp)
                                        .height(41.dp)
                                        .background(
                                            color = if (letter.isCorrect == null) Color(0xFF2E3740) else if (letter.isCorrect) Color(
                                                0xFF305B31
                                            ) else Color(0xFFC50000),
                                            shape = RoundedCornerShape(10.dp)
                                        )
                                        .then(if (!letter.isSelected && state.isYourTurn) Modifier.clickable {
                                            onEvent(
                                                GameIntent.GuessLetter(
                                                    GuessLetter(
                                                        state.gameRouteUi.roomId,
                                                        state.gameRouteUi.userUid,
                                                        letter.char
                                                    )
                                                )
                                            )
                                        } else Modifier),


                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(text = letter.char, color = Color(0xFFC4C4C4))
                                }
                            }
                        }
                    )


                    Spacer(modifier = Modifier.height(25.dp))


                }


            }






            if (state.gameOver != null || state.isReadyPlayerSheetOpen || state.isGameOver || state.isBack) {
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
                    dragHandle = {


                    }
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
                                .align(
                                    Alignment.TopCenter
                                )
                        )
                        Column(
                            modifier = Modifier
                                .padding(top = 17.dp)
                                .fillMaxWidth()
                        ) {

                            Spacer(modifier = Modifier.height(16.dp))

                            if (!state.isBack) {
                                if (!state.isReadyPlayerSheetOpen) {
                                    Text(
                                        text = if (state.playerEliminated == null) {
                                            if (state.gameOver?.winner.isNullOrBlank()) {
                                                "It's a Draw! \uD83E\uDD1D"
                                            } else {
                                                if (state.gameOver.winner == state.gameRouteUi.username) {
                                                    "Congratulations! \uD83C\uDF89"
                                                } else {
                                                    "Round Over! \uD83C\uDFC6"
                                                }
                                            }
                                        } else {
                                            "You Are Eliminated! ❌"
                                        },
                                        style = Inter.copy(
                                            color = Color(0xFFCFCFCF),
                                            fontSize = 16.sp,
                                            letterSpacing = (0.7).sp,
                                        ),
                                        modifier = Modifier
                                            .padding(
                                                horizontal = 10.dp,
                                                vertical = 4.dp
                                            )
                                            .align(Alignment.CenterHorizontally)
                                    )
                                    Spacer(modifier = Modifier.height(36.dp))

                                    Text(
                                        text = if (state.playerEliminated == null) {
                                            if (state.gameOver?.winner.isNullOrBlank()) {
                                                "No one could guess the word. Better luck next time!"
                                            } else {
                                                if (state.gameOver.winner == state.gameRouteUi.username) {
                                                    "You guessed the word correctly and won this round!"
                                                } else {
                                                    "${state.gameOver.winner} guessed the word correctly and won this round."
                                                }
                                            }
                                        } else {
                                            "Your guess was incorrect. You are out of this round."
                                        },
                                        style = Inter.copy(
                                            color = Color(0xFFCFCFCF),
                                            fontSize = 15.sp
                                        ),
                                        modifier = Modifier
                                            .padding(horizontal = 59.dp)
                                            .align(
                                                Alignment.CenterHorizontally
                                            ),
                                        textAlign = TextAlign.Center
                                    )


                                    Spacer(modifier = Modifier.height(55.dp))

                                    Box(
                                        modifier = Modifier
                                            .padding(horizontal = 18.dp)
                                            .fillMaxWidth()
                                            .height(45.dp)
                                            .background(
                                                color = Color(0xFFC40001),
                                                shape = RoundedCornerShape(15.dp)
                                            )
                                            .clickable {
                                                navController.popBackStack(
                                                    Routes.HomeScreen,
                                                    inclusive = false
                                                )

                                            }
                                    ) {
                                        Text(
                                            text = "Exit",
                                            style = Inter.copy(
                                                color = Color.White,
                                                fontSize = 18.sp,
                                                fontWeight = FontWeight.Normal
                                            ),
                                            modifier = Modifier
                                                .align(
                                                    Alignment.Center
                                                )

                                        )
                                    }

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Box(
                                        modifier = Modifier
                                            .padding(horizontal = 18.dp)
                                            .fillMaxWidth()
                                            .height(45.dp)
                                            .background(
                                                color = Color.Transparent,
                                                shape = RoundedCornerShape(15.dp)
                                            )
                                            .border(
                                                width = 1.dp,
                                                color = Color.White.copy(0.5f),
                                                shape = RoundedCornerShape(15.dp)
                                            )
                                            .clickable {
                                                navController.popBackStack(
                                                    Routes.HomeScreen,
                                                    inclusive = false
                                                )

                                            }
                                    ) {
                                        Text(
                                            text = "View Results",
                                            style = Inter.copy(
                                                color = Color.White,
                                                fontSize = 18.sp,
                                                fontWeight = FontWeight.Normal
                                            ),
                                            modifier = Modifier
                                                .align(
                                                    Alignment.Center
                                                )

                                        )
                                    }


                                } else {
                                    Text(
                                        text = "Players",
                                        style = Inter.copy(
                                            color = Color(0xFFCFCFCF),
                                            fontSize = 14.sp,
                                            letterSpacing = (0.7).sp,
                                        ),
                                        modifier = Modifier
                                            .padding(
                                                horizontal = 10.dp,
                                                vertical = 4.dp
                                            )
                                            .align(Alignment.CenterHorizontally)
                                    )

                                    Text(
                                        text = "${state.joinedList.size}/${state.gameRouteUi.maxPlayers}",
                                        style = Inter.copy(
                                            color = Color(0xFFCFCFCF).copy(0.7f),
                                            fontSize = 10.sp,
                                            letterSpacing = (0.7).sp,
                                        ),
                                        modifier = Modifier
                                            .padding(
                                                horizontal = 10.dp,
                                                vertical = 4.dp
                                            )
                                            .align(Alignment.CenterHorizontally)
                                    )
                                    Spacer(modifier = Modifier.height(12.dp))
                                    LazyColumn(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {

                                        items(state.joinedList) { player ->
                                            Row(modifier = Modifier.fillMaxWidth()) {


                                                Column(modifier = Modifier.weight(0.5f)) {
                                                    Text(
                                                        text = player.username,
                                                        maxLines = 1,
                                                        overflow = TextOverflow.Ellipsis,
                                                        style = Inter.copy(
                                                            color = Color(0xFFCFCFCF).copy(0.7f),
                                                            fontSize = 12.sp,
                                                            letterSpacing = (0.8).sp,
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
                                                            color = if (player.isReady) Color(
                                                                0xFF3EB481
                                                            ).copy(0.7f) else Color(0xFFC40100).copy(
                                                                0.7f
                                                            ),
                                                            fontSize = 12.sp,
                                                            letterSpacing = (0.8).sp,
                                                        ),
                                                        modifier = Modifier
                                                            .align(Alignment.Start)

                                                    )
                                                }


                                            }
                                            Spacer(modifier = Modifier.height(10.dp))


                                        }


                                    }


                                }

                            } else {


                                Text(
                                    text = "Are you sure you want to leave?",
                                    style = Inter.copy(
                                        color = Color(0xFFCFCFCF),
                                        fontSize = 16.sp,
                                        letterSpacing = (0.7).sp,
                                    ), textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .padding(
                                            horizontal = 18.dp,
                                            vertical = 4.dp
                                        )
                                        .align(Alignment.CenterHorizontally)
                                )
                                Spacer(modifier = Modifier.height(28.dp))


                                Text(
                                    text = "Leaving will eliminate you from this game. Do you still want to exit?",
                                    style = Inter.copy(
                                        color = Color(0xFFCFCFCF).copy(0.8f),
                                        fontSize = 15.sp,
                                        letterSpacing = (0.7).sp,
                                    ), textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .padding(
                                            horizontal = 18.dp,
                                            vertical = 4.dp
                                        )
                                        .align(Alignment.CenterHorizontally)
                                )

                                Spacer(modifier = Modifier.height(55.dp))


                                Row(
                                    modifier = Modifier
                                        .padding(horizontal = 18.dp)
                                        .fillMaxWidth()
                                ) {


                                    Box(
                                        modifier = Modifier

                                            .weight(1f)
                                            .height(45.dp)
                                            .background(
                                                color = Color.Transparent,
                                                shape = RoundedCornerShape(15.dp)
                                            )
                                            .border(
                                                width = 1.dp,
                                                color = Color.White.copy(0.5f),
                                                shape = RoundedCornerShape(15.dp)
                                            )
                                            .clickable {
                                                onEvent(GameIntent.GoBack)

                                            }
                                    ) {
                                        Text(
                                            text = "Stay",
                                            style = Inter.copy(
                                                color = Color.White,
                                                fontSize = 18.sp,
                                                fontWeight = FontWeight.Normal
                                            ),
                                            modifier = Modifier
                                                .align(
                                                    Alignment.Center
                                                )

                                        )
                                    }

                                    Spacer(modifier = Modifier.width(10.dp))




                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .height(45.dp)
                                            .background(
                                                color = Color(0xFFC40001),
                                                shape = RoundedCornerShape(15.dp)
                                            )
                                            .clickable {
                                                onEvent(
                                                    GameIntent.LeaveRoom(
                                                        LeaveRoom(
                                                            state.gameRouteUi.roomId,
                                                            state.gameRouteUi.userUid
                                                        )
                                                    )
                                                )

                                            }
                                    ) {
                                        Text(
                                            text = "Exit game",
                                            style = Inter.copy(
                                                color = Color.White,
                                                fontSize = 18.sp,
                                                fontWeight = FontWeight.Normal
                                            ),
                                            modifier = Modifier
                                                .align(
                                                    Alignment.Center
                                                )

                                        )
                                    }


                                }

                            }



                            Spacer(modifier = Modifier.height(20.dp))


                        }


                    }
                }
            }


        }
    }
}




