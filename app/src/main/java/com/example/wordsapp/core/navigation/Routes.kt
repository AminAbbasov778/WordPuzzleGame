package com.example.wordsapp.core.navigation

import com.example.wordsapp.home.presentation.GameRouteUi
import com.example.wordsapp.home.presentation.RoomUi
import kotlinx.serialization.Contextual
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


}