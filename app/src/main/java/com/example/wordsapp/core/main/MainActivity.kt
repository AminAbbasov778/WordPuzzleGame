package com.example.wordsapp.core.main

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.wordsapp.core.navigation.MainNavGraph
import com.example.wordsapp.core.navigation.Routes

import com.example.wordsapp.splash.SplashScreen
import com.example.wordsapp.ui.theme.WordsAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @SuppressLint("StateFlowValueCalledInComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()


            WordsAppTheme {
                MainNavGraph(navController, startDestination = Routes.SplashRoot)
            }
        }
    }
}


