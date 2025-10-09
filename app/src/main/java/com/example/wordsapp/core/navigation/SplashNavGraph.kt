package com.example.wordsapp.core.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.wordsapp.core.utils.Navigate
import com.example.wordsapp.splash.screen.SplashScreen
import com.example.wordsapp.splash.viewmodel.SplashViewModel

fun NavGraphBuilder.splashNavGraph(navController: NavHostController) {

    composable<Routes.SplashScreen> {
        val viewModel: SplashViewModel = hiltViewModel()
        val state by viewModel.state.collectAsState()
        viewModel.Navigate(navController)
        SplashScreen( state)
    }


}