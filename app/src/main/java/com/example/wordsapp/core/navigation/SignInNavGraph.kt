package com.example.wordsapp.core.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.wordsapp.auth.signin.SignInScreen
import com.example.wordsapp.auth.signin.SignInViewModel
import com.example.wordsapp.core.utils.Navigate

fun NavGraphBuilder.signInNavGraph (navController: NavHostController){

    composable<Routes.SignInScreen> {
        val viewModel: SignInViewModel = hiltViewModel()
        val state by viewModel.state.collectAsStateWithLifecycle()
        viewModel.Navigate(navController)
        SignInScreen(onIntent = {viewModel.OnEvent(it)},state)

    }

}

