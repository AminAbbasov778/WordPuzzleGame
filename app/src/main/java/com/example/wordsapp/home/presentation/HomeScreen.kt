package com.example.wordsapp.home.presentation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.wordsapp.R
import com.example.wordsapp.auth.signup.SignUpIntent
import com.example.wordsapp.auth.signup.SignUpState
import com.example.wordsapp.core.navigation.Routes
import com.example.wordsapp.home.presentation.components.HomeHeader
import com.example.wordsapp.home.presentation.components.RoomsBody
import com.example.wordsapp.home.presentation.components.StatusGuideFooter
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavHostController, onIntent: (HomeIntents) -> Unit, state: HomeState ) {

    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val navBackStackEntry = navController.currentBackStackEntryAsState()


    LaunchedEffect(navBackStackEntry.value) {
        Log.e("Home", "HomeScreen:Check connect ", )
            onIntent(HomeIntents.ConnectSocket)

    }

    Scaffold {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF222831))
                .systemBarsPadding()
        )
        {


                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .then(
                            if (state.isStatusGuideVisible) Modifier.blur(4.dp) else Modifier
                        )
                        .padding(horizontal = 20.dp)
                ) {



                    HomeHeader(username = state.username,onIntent = onIntent,navController)
                    Spacer(modifier = Modifier.height(46.dp))

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
                        rooms = state.rooms,
                        getRelativeTime = { it.toString() },
                        modifier = Modifier.weight(1f),
                        isStatusGuideVisible = { onIntent(HomeIntents.ChangeStatusGuideVisibility) },
                        onIntent = { intent ->
                            when(intent) {
                                is HomeIntents.JoinRoom -> {
                                    onIntent(intent)
                                }
                                else -> {}
                            }
                        }, navController = navController,username = state.username,userUid = state.userUid ?: ""
                    )


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
