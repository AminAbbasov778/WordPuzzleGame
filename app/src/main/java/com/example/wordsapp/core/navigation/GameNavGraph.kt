package com.example.wordsapp.core.navigation

import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.wordsapp.game.GameScreen
import com.example.wordsapp.game.GameViewModel
import kotlin.reflect.typeOf
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.wordsapp.home.presentation.GameRouteUi


fun NavGraphBuilder.gameNavGraph(navHostController: NavHostController) {
    composable<Routes.GameScreen>(typeMap = mapOf(typeOf<GameRouteUi>() to createNavType<GameRouteUi>())) {
       val viewModel : GameViewModel  =  hiltViewModel()
        val state by viewModel.state.collectAsStateWithLifecycle()
        val arguments = it.toRoute<Routes.GameScreen>()
        GameScreen(navHostController,state,arguments.gameRouteUi,onEvent = {viewModel.onEvent(it)})
    }
}