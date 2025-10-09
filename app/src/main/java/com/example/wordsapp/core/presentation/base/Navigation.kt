package com.example.wordsapp.core.presentation.base

import com.example.wordsapp.core.navigation.Routes

open class Navigation(
    val route: Routes?,
    val shouldPop : Boolean = false,
    val popInclusive: Boolean = false,
    val popUpToRoute: Routes? = null,
    val popUpToInclusive: Boolean = true,
    val isLaunchSingleTop: Boolean = true
)