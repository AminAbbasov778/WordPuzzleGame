package com.example.wordsapp.core.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.wordsapp.home.presentation.HomeScreen
import com.example.wordsapp.home.presentation.HomeViewModel

fun NavGraphBuilder.homeNavGraph(navController: NavHostController){
    composable<Routes.HomeScreen> {
        val viewModel: HomeViewModel = hiltViewModel()
        val state by viewModel.roomsState.collectAsState()
        HomeScreen(navController, onIntent = {viewModel.onEvent(it)}, state)
    }
}