package com.example.wordsapp.core.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.wordsapp.core.utils.Navigate
import com.example.wordsapp.history.presentation.model.HistoryRouteUi
import com.example.wordsapp.history.presentation.screen.HistoryScreen
import com.example.wordsapp.history.presentation.viewmodel.HistoryViewModel
import kotlin.reflect.typeOf

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.historyNavGraph(navHostController: NavHostController) {
    composable<Routes.HistoryScreen>(typeMap = mapOf(typeOf<HistoryRouteUi>() to createNavType<HistoryRouteUi>())) {

        val viewModel : HistoryViewModel  =  hiltViewModel()
        val state by viewModel.state.collectAsStateWithLifecycle()
        val argument = it.toRoute<Routes.HistoryScreen>()
        viewModel.Navigate(navHostController)

        HistoryScreen( state, event = {viewModel.OnEvent(it)},argument.historyRouteUi)
    }
}


