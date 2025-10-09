package com.example.wordsapp.history.presentation.navigation

import com.example.wordsapp.core.navigation.Routes
import com.example.wordsapp.core.presentation.base.Navigation
import com.example.wordsapp.history.presentation.model.HistoryRouteUi

sealed class HistoryNavigation(
    route: Routes,
    shouldPop: Boolean = false,
    popInclusive: Boolean = false,
    popUpTo: Routes? = null,
    popUpToInclusive: Boolean = false
) : Navigation(route, shouldPop,
    popInclusive,
    popUpTo,
    popUpToInclusive
     ) {

    data class ToPage(
        val destination: Routes
    ) : HistoryNavigation(
        route = destination,
        shouldPop = false,

    )
    data object HistoryScreenToHomeScreen : HistoryNavigation(route = Routes.HomeScreen, shouldPop = false, popInclusive = true, popUpTo = Routes.HistoryRoot, popUpToInclusive = false)


}