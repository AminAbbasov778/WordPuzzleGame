package com.example.wordsapp.auth.signup

import com.example.wordsapp.core.navigation.Routes
import com.example.wordsapp.core.presentation.base.Navigation

sealed class SignUpNavigation(
    route: Routes,
    shouldPop: Boolean = false,
    popInclusive: Boolean = false,
    popUpToRoute: Routes? = null,
    popUpToInclusive: Boolean = true
) : Navigation(route, shouldPop, popInclusive,popUpToRoute,popUpToInclusive) {


    data object SignUpScreenToSignInScreen :
        SignUpNavigation(Routes.SignInScreen, shouldPop = false, popInclusive = true,popUpToRoute = Routes.SignUpScreen,popUpToInclusive = true)
    data object SignUpScreenToHomeScreen :
        SignUpNavigation(Routes.HomeScreen, shouldPop = false, popInclusive = true,popUpToRoute = Routes.SignUpScreen,popUpToInclusive = true)


}

