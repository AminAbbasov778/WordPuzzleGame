package com.example.wordsapp.core.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.wordsapp.history.presentation.HistoryRouteUi
import com.example.wordsapp.history.presentation.HistoryScreen
import com.example.wordsapp.history.presentation.HistoryViewModel
import kotlin.reflect.typeOf

fun NavGraphBuilder.historyNavGraph(navHostController: NavHostController) {
    composable<Routes.HistoryScreen>(typeMap = mapOf(typeOf<HistoryRouteUi>() to createNavType<HistoryRouteUi>())) {

        val viewModel : HistoryViewModel  =  hiltViewModel()
        val state by viewModel.state.collectAsStateWithLifecycle()
        val argument = it.toRoute<Routes.HistoryScreen>()

        HistoryScreen(navHostController ,state, event = {viewModel.onEvent(it)},argument.historyRouteUi)
    }
}


