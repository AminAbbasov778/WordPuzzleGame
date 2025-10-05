package com.example.wordsapp.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.wordsapp.R
import com.example.wordsapp.core.navigation.MainNavGraph
import com.example.wordsapp.core.navigation.Routes

@Composable
fun SplashScreen (navController: NavHostController , state: SplashState){
    Box(modifier = Modifier.fillMaxSize().background(Color(0xFF222831)), contentAlignment = Alignment.Center){



        if(state.isLoaded){
            Icon(painter = painterResource(R.drawable.ic_app), contentDescription = "splash screen", tint = Color.Unspecified, modifier = Modifier.align(
                Alignment.Center))


        }else{


            if (state.isSignedIn) {
               navController.navigate(Routes.HomeRoot){
                   popUpTo(Routes.SplashScreen){
                       inclusive = true
                   }
               }

            } else {
               navController.navigate(Routes.SignInRoot){
                   popUpTo(Routes.SplashScreen){
                       inclusive = true
                   }
               }
            }


        }

    }
}
