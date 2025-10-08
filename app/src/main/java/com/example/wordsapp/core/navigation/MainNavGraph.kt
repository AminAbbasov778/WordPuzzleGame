package com.example.wordsapp.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.navigation
import com.example.wordsapp.history.presentation.HistoryRouteUi
import com.example.wordsapp.home.presentation.GameRouteUi

@Composable
fun MainNavGraph(navController: NavHostController, startDestination: Routes) {
    NavHost(navController = navController, startDestination = startDestination) {


        navigation<Routes.SignInRoot>(startDestination = Routes.SignInScreen) {
            signInNavGraph(navController)

        }

        navigation<Routes.SignUpRoot>(startDestination = Routes.SignUpScreen) {
            signUpNavGraph(navController)

        }

        navigation<Routes.HomeRoot>(startDestination = Routes.HomeScreen) {
            homeNavGraph(navController)
        }

        val defaultGameRoute = GameRouteUi()

        navigation<Routes.GameRoot>(
            startDestination = Routes.GameScreen(defaultGameRoute)
        ) {
            gameNavGraph(navController)
        }

        navigation<Routes.SplashRoot>(
            startDestination = Routes.SplashScreen
        ) {
            splashNavGraph(navController)
        }

        val defaultHistoryRoute = HistoryRouteUi()

        navigation<Routes.HistoryRoot>(
            startDestination = Routes.HistoryScreen(defaultHistoryRoute)
        ) {
            historyNavGraph(navController)
        }


    }
}