package com.example.wordsapp.home.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.wordsapp.R
import com.example.wordsapp.home.presentation.components.CreateRoom
import com.example.wordsapp.home.presentation.components.HomeHeader
import com.example.wordsapp.home.presentation.components.RoomsBody
import com.example.wordsapp.home.presentation.components.StatusGuideFooter
import com.example.wordsapp.home.presentation.intent.HomeIntents
import com.example.wordsapp.home.presentation.state.HomeState
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen( onIntent: (HomeIntents) -> Unit, state: HomeState) {

    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)





    Scaffold {
        Box(
            modifier = Modifier
                .fillMaxSize().background(Color(0xFF2E3641))
                .systemBarsPadding()
        )
        {


                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .then(
                            if (state.isStatusGuideVisible) Modifier.blur(4.dp) else Modifier
                        )

                ) {



                    HomeHeader(username = state.username, onIntent = onIntent, state = state)

                    Column(modifier = Modifier.fillMaxSize().background(Color(0xFF222831)).padding(horizontal = 20.dp)){
                       Spacer(modifier = Modifier.height(16.dp))
                        CreateRoom()

                        if (state.isLoading) {

                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.stickman_walking))
                                val progress by animateLottieCompositionAsState(
                                    composition,
                                    iterations = LottieConstants.IterateForever,
                                    isPlaying = composition != null
                                )
                                LottieAnimation(
                                    composition = composition,
                                    progress = { progress },
                                    modifier = Modifier.size(100.dp)
                                )
                            }


                        } else {

                            RoomsBody(
                                modifier = Modifier.weight(1f),
                                isStatusGuideVisible = { onIntent(HomeIntents.ChangeStatusGuideVisibility) },
                                onIntent = { onIntent(it)
                                },username = state.username,userUid = state.userUid ?: ""
                            , state = state)


                        }

                        if (state.isStatusGuideVisible) {
                            ModalBottomSheet(modifier = Modifier
                                .padding(horizontal = 20.dp)
                                .navigationBarsPadding().padding(bottom = 20.dp)
                                .fillMaxWidth(),
                                onDismissRequest = {
                                    scope.launch { sheetState.hide() }
                                    onIntent(HomeIntents.ChangeStatusGuideVisibility)
                                },
                                sheetState = sheetState,
                                containerColor = Color(0xFF2E3740),
                                shape = RoundedCornerShape(16.dp),
                                tonalElevation = 8.dp,
                                dragHandle = {


                                }
                            ) {
                                StatusGuideFooter(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding()
                                )
                            }
                        }
                    }



                }
        }
                    }

    }



