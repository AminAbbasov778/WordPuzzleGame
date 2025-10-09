package com.example.wordsapp.core.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.wordsapp.core.utils.Navigate
import com.example.wordsapp.game.presentation.screen.GameScreen
import com.example.wordsapp.game.presentation.viewmodel.GameViewModel
import com.example.wordsapp.home.presentation.model.GameRouteUi
import kotlin.reflect.typeOf


fun NavGraphBuilder.gameNavGraph(navHostController: NavHostController) {
    composable<Routes.GameScreen>(typeMap = mapOf(typeOf<GameRouteUi>() to createNavType<GameRouteUi>())) {
       val viewModel : GameViewModel  =  hiltViewModel()
        val state by viewModel.state.collectAsStateWithLifecycle()
        val arguments = it.toRoute<Routes.GameScreen>()
        viewModel.Navigate(navHostController)
        GameScreen(state,arguments.gameRouteUi,onEvent = {viewModel.OnEvent(it)})
    }
}