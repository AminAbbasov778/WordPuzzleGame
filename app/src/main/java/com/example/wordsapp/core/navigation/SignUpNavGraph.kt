package com.example.wordsapp.core.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.wordsapp.auth.signup.SignUpScreen
import com.example.wordsapp.auth.signup.SignUpViewModel

fun NavGraphBuilder.signUpNavGraph(navController: NavHostController){
    composable<Routes.SignUpScreen> {
        val viewModel: SignUpViewModel = hiltViewModel()
        val state by viewModel.state.collectAsState()
        SignUpScreen(navController, onIntent = {viewModel.onIntent(it)},state)
    }
}