package com.example.wordsapp.core.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.wordsapp.auth.signup.SignUpScreen
import com.example.wordsapp.auth.signup.SignUpViewModel
import com.example.wordsapp.core.utils.Navigate

fun NavGraphBuilder.signUpNavGraph(navController: NavHostController){
    composable<Routes.SignUpScreen> {
        val viewModel: SignUpViewModel = hiltViewModel()
        val state by viewModel.state.collectAsStateWithLifecycle()
        viewModel.Navigate(navController)
        SignUpScreen( onIntent = {viewModel.OnEvent(it)},state)
    }
}