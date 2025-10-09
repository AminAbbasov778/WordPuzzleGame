package com.example.wordsapp.core.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.wordsapp.core.utils.Navigate
import com.example.wordsapp.home.presentation.screen.HomeScreen
import com.example.wordsapp.home.presentation.viewmodel.HomeViewModel

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.homeNavGraph(navController: NavHostController){
    composable<Routes.HomeScreen> {
        val viewModel: HomeViewModel = hiltViewModel()
        val state by viewModel.state.collectAsState()
        viewModel.Navigate(navController)
        HomeScreen( onIntent = {viewModel.OnEvent(it)}, state)
    }
}