package com.example.wordsapp.home.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.wordsapp.R
import com.example.wordsapp.core.navigation.Routes
import com.example.wordsapp.home.presentation.HomeIntents
import com.example.wordsapp.ui.theme.Inknut40
import com.example.wordsapp.ui.theme.Inter

@Composable
fun HomeHeader(username: String,onIntent: (HomeIntents) -> Unit,navController : NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Hanged",
                style = Inknut40.copy(color = Color(0xFFC4C4C4), fontSize = 24.sp)
            )
            Text(
                text = "Welcome, $username",
                style = Inter.copy(color = Color(0xFFC4C4C4), fontSize = 12.sp)
            )
        }

        Icon(
            painter = painterResource(R.drawable.ic_logout),
            contentDescription = "logout",
            tint = Color.Unspecified,
            modifier = Modifier.align(Alignment.CenterVertically).clickable{
                onIntent(HomeIntents.SignOut)
                navController.navigate(Routes.SignInRoot)
            }
        )
    }
}