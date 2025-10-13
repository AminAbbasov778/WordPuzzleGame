package com.example.wordsapp.history.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.wordsapp.history.presentation.components.GameDetail
import com.example.wordsapp.history.presentation.components.HistoryItem
import com.example.wordsapp.history.presentation.components.HistoryTabs
import com.example.wordsapp.history.presentation.components.TopBar
import com.example.wordsapp.history.presentation.enums.HistoryTabs
import com.example.wordsapp.history.presentation.intent.HistoryIntent
import com.example.wordsapp.history.presentation.model.HistoryRouteUi
import com.example.wordsapp.history.presentation.state.HistoryState
import com.example.wordsapp.home.presentation.components.EmptyHistory
import com.example.wordsapp.home.presentation.components.EmptyLeaderBoard
import com.example.wordsapp.home.presentation.components.LeaderboardItem
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalPagerApi::class)
@Composable
fun HistoryScreen(
    state: HistoryState,
    event: (HistoryIntent) -> Unit,
    historyRouteUi: HistoryRouteUi,
) {
    val pagerState = rememberPagerState(initialPage =
        if (state.currentTab == HistoryTabs.HISTORY) 0 else 1
    )

    LaunchedEffect(Unit) {
        event(HistoryIntent.GetUser(historyRouteUi.userId))
        event(HistoryIntent.GetGames(historyRouteUi.userId))
    }

    LaunchedEffect(pagerState.currentPage) {
        val newTab = if (pagerState.currentPage == 0) HistoryTabs.HISTORY else HistoryTabs.LEADERBOARD
        if (state.currentTab != newTab) {
            event(HistoryIntent.ChangeTab(newTab))
        }
    }
    LaunchedEffect(state.currentTab) {
        val targetPage = if (state.currentTab == HistoryTabs.HISTORY) 0 else 1
        if (pagerState.currentPage != targetPage) {
            pagerState.animateScrollToPage(targetPage)
        }
    }

    Scaffold {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF222831))
                .systemBarsPadding()
                .padding(horizontal = 20.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .then(if (state.isGameDetailVisible) Modifier.blur(4.dp) else Modifier)
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                TopBar(state, onEvent = {event(it)})
                Spacer(modifier = Modifier.height(14.dp))

                HistoryTabs(
                    state = state,
                    onEvent = { event(it) }
                )

                Spacer(modifier = Modifier.height(14.dp))

                if(!state.isLoading){
                    HorizontalPager(
                        count = 2,
                        state = pagerState,
                        modifier = Modifier.fillMaxSize()
                    ) { page ->
                        when (page) {
                            0 -> {
                                if(state.gameHistory.isEmpty()){
                                    Box(modifier = Modifier.fillMaxSize()){
                                        EmptyHistory()
                                    }

                                }else{

                                       HistoryItem(
                                           state.gameHistory,
                                           userId = state.user?.data?.user?.userId ?: "1",
                                           onEvent = { event(it) }
                                       )


                                }



                            }

                            1 -> {
                                if(state.leaderboard.isEmpty()) {
                                    EmptyLeaderBoard()
                                }else{
                                    LeaderboardItem(state,historyRouteUi)

                                }



                            }
                        }
                    }

                }else{
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
                }

            }

            if (state.isGameDetailVisible) {
                Box(modifier = Modifier.fillMaxSize()) {
                    state.gameDetail?.let {
                        GameDetail(it, onEvent = { event(it) })
                    }
                }
            }
        }
    }
}



