package com.example.wordsapp.home.presentation.navigation

import com.example.wordsapp.core.navigation.Routes
import com.example.wordsapp.core.presentation.base.Navigation
import com.example.wordsapp.history.presentation.model.HistoryRouteUi
import com.example.wordsapp.home.presentation.model.GameRouteUi

sealed class HomeNavigation(
    route: Routes,
    shouldPop: Boolean = false,
    popInclusive: Boolean = false,
    popUpToRoute: Routes? = null,
    popUpToInclusive: Boolean = true
) : Navigation(route, shouldPop,
    popInclusive,popUpToRoute,popUpToInclusive) {

    data class HomeScreenToHistoryScreen(val historyRouteUi: HistoryRouteUi) :
        HomeNavigation(Routes.HistoryScreen(historyRouteUi), shouldPop = false, popInclusive = true,popUpToRoute = Routes.HomeScreen,popUpToInclusive = true)
    data class HomeScreenToGameScreen(val gameRouteUi: GameRouteUi,) :
        HomeNavigation(Routes.GameScreen(gameRouteUi), shouldPop = false, popInclusive = true,popUpToRoute = Routes.HomeScreen,popUpToInclusive = true)
    data object HomeScreenToSignInScreen :
        HomeNavigation(Routes.SignInScreen, shouldPop = false, popInclusive = true,popUpToRoute = Routes.HomeScreen,popUpToInclusive = true)
}