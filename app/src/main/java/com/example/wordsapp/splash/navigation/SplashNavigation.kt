package com.example.wordsapp.splash.navigation

import com.example.wordsapp.core.navigation.Routes
import com.example.wordsapp.core.presentation.base.Navigation

sealed class SplashNavigation(
    route: Routes,
    shouldPop: Boolean = false,
    popInclusive: Boolean = false,
    popUpToRoute: Routes? = null,
    popUpToInclusive: Boolean = true,
) : Navigation(route, shouldPop, popInclusive,popUpToRoute,popUpToInclusive
) {

    data class ToPage(
        val destination: Routes
    ) : SplashNavigation(
        route = destination,
        shouldPop = false,
    )
    data object SplashScreenToSignInScreen :
        SplashNavigation(Routes.SignInScreen, shouldPop = false,popInclusive = true,popUpToRoute = Routes.SplashScreen,popUpToInclusive = true)

    data object SplashScreenToHomeScreen :
        SplashNavigation(Routes.HomeScreen, shouldPop = false,popInclusive = true,popUpToRoute = Routes.SplashScreen,popUpToInclusive = true)
}

