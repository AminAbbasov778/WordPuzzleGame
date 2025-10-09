package com.example.wordsapp.splash.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.example.wordsapp.R
import com.example.wordsapp.core.navigation.Routes
import com.example.wordsapp.splash.state.SplashState

@Composable
fun SplashScreen ( state: SplashState){
    Box(modifier = Modifier.fillMaxSize().background(Color(0xFF222831)), contentAlignment = Alignment.Center){



        if(state.isLoaded){
            Icon(painter = painterResource(R.drawable.ic_app), contentDescription = "splash screen", tint = Color.Unspecified, modifier = Modifier.align(
                Alignment.Center))


        }




    }
}
