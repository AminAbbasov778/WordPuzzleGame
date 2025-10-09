package com.example.wordsapp.auth.signin

import com.example.wordsapp.core.navigation.Routes
import com.example.wordsapp.core.presentation.base.Navigation

sealed class SignInNavigation(
    route: Routes,
    shouldPop: Boolean = false,
    popInclusive: Boolean = false,
    popUpToRoute: Routes? = null,
    popUpToInclusive: Boolean = true,
) : Navigation(route, shouldPop, popInclusive,popUpToRoute,popUpToInclusive
    ) {


    data object SignInScreenToHomeScreen :
        SignInNavigation(Routes.HomeScreen, shouldPop = false,popInclusive = true,popUpToRoute = Routes.SignInScreen,popUpToInclusive = true)
    data object SignInScreenToSignUpScreen :
        SignInNavigation(Routes.SignUpScreen, shouldPop = false,popInclusive = true,popUpToRoute = Routes.SignInScreen,popUpToInclusive = true)
}

