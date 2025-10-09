package com.example.wordsapp.core.navigation

import com.example.wordsapp.history.presentation.model.HistoryRouteUi
import com.example.wordsapp.home.presentation.model.GameRouteUi
import kotlinx.serialization.Serializable

@Serializable
sealed class Routes {


    @Serializable
    data object SignInScreen : Routes()

    @Serializable
    data object SignUpScreen : Routes()

    @Serializable
    data object HomeScreen : Routes()

    @Serializable
    data object HomeRoot : Routes()


    @Serializable
    data object SignInRoot: Routes()

    @Serializable
    data object SignUpRoot : Routes()

    @Serializable
    data class GameScreen( val gameRouteUi: GameRouteUi) : Routes()

    @Serializable
    data object GameRoot : Routes()

    @Serializable
    data object SplashScreen : Routes()

    @Serializable
    data object SplashRoot : Routes()

    @Serializable
    data class HistoryScreen(val historyRouteUi: HistoryRouteUi) : Routes()

    @Serializable
    data object HistoryRoot : Routes()


}