package com.example.wordsapp.core.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.core.net.toUri
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.navOptions
import com.example.wordsapp.core.navigation.Routes
import com.example.wordsapp.core.presentation.base.BaseViewModel

import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Locale


@Composable
fun BaseViewModel<*, *, *>.Navigate(navController: NavController) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(Unit) {
        lifecycleOwner.lifecycleScope.launch {
            navigation.collectLatest {
                if (it.shouldPop) {
                    it.route?.let { route ->
                        navController.popBackStack(route = route, it.popInclusive)
                    } ?: run {
                        navController.safePopBackStack()
                    }
                } else {
                    navController.safeNavigate(
                        route = it.route
                            ?: throw IllegalArgumentException("Navigation with shouldPop=false cannot have null route"),
                        popUpToRoute = it.popUpToRoute,
                        inclusive = it.popUpToInclusive,
                        isLaunchSingleTop = it.isLaunchSingleTop

                    )
                }
            }
        }
    }
}


fun NavController.safePopBackStack() {
    if (this.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
        this.popBackStack()
    }
}

fun NavController.safeNavigate(
    route: Routes,
    popUpToRoute: Routes? = null,
    inclusive: Boolean = false,
    isLaunchSingleTop: Boolean
) {
    this.navigate(
        route = route,
        navOptions = navOptions {
            launchSingleTop = isLaunchSingleTop
            popUpToRoute?.let {
                popUpTo(it) {
                    this.inclusive = inclusive
                }
            }
        })
}