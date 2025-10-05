package com.example.wordsapp.core.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.wordsapp.auth.signin.SignInScreen
import com.example.wordsapp.auth.signin.SignInViewModel

fun NavGraphBuilder.signInNavGraph (navController: NavHostController){

    composable<Routes.SignInScreen> {
        val viewModel: SignInViewModel = hiltViewModel()
        val state by viewModel.state.collectAsState()
        SignInScreen(navController, onIntent = {viewModel.onEvent(it)},state)

    }

}

