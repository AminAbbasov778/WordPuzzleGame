package com.example.wordsapp.game.presentation.navigation

import com.example.wordsapp.core.navigation.Routes
import com.example.wordsapp.core.presentation.base.Navigation
import com.example.wordsapp.history.presentation.model.HistoryRouteUi

sealed class GameNavigation(
    route: Routes,
    shouldPop: Boolean = false,
    popInclusive: Boolean = false,
    popUpTo: Routes? = null,
    popUpToInclusive: Boolean = false
) : Navigation(route, shouldPop,
    popInclusive, popUpTo, popUpToInclusive) {

    data class GameScreenToHistoryScreen(val historyRouteUi: HistoryRouteUi) :
        GameNavigation(Routes.HistoryScreen(historyRouteUi), shouldPop = false,  popInclusive = true, popUpTo = Routes.GameRoot, popUpToInclusive = true )


      data object  GameScreenToHomeScreen : GameNavigation(Routes.HomeScreen, shouldPop = false,  popInclusive = true, popUpTo = Routes.GameRoot, popUpToInclusive = true )


}