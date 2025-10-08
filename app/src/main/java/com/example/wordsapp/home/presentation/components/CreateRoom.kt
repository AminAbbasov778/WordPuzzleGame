package com.example.wordsapp.home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradient
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.wordsapp.R
import com.example.wordsapp.core.navigation.Routes
import com.example.wordsapp.home.presentation.HomeIntents
import com.example.wordsapp.ui.theme.Inknut40
import com.example.wordsapp.ui.theme.Inter


@Composable
fun CreateRoom() {
    val config = LocalConfiguration.current
    val screenHeight = config.screenHeightDp.dp
    val screenWidth = config.screenWidthDp.dp

    Box(
        modifier = Modifier
            .fillMaxWidth().clip(RoundedCornerShape(10.dp))
            .background(
                brush = Brush.linearGradient(
                    listOf(Color(0xFFC40100), Color(0xFFA00100))
                ),
                shape = RoundedCornerShape(10.dp)
            )
            .padding(vertical = screenHeight / 60)
            .padding(horizontal = screenWidth / 25)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = screenWidth / 8, y = (-screenHeight / 20)) // yarısı çöldə
                .size(screenHeight / 10)
                .background(
                    color = Color(0xFFD9D9D9).copy(alpha = 0.08f),
                    shape = RoundedCornerShape(screenHeight / 20)
                )
        )

        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = screenWidth / 6, y = screenHeight / 15) // yarısı çöldə
                .size(screenHeight / 10)
                .background(
                    color = Color(0xFFD9D9D9).copy(alpha = 0.08f),
                    shape = RoundedCornerShape(screenHeight / 20)
                )
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            Text(
                text = "Ready to Play?",
                style = Inter.copy(
                    fontSize = 22.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Challenge your vocabulary skills with friends around the world",
                style = Inter.copy(
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.8f),
                    fontWeight = FontWeight.SemiBold
                )
            )

            Spacer(modifier = Modifier.height(screenHeight / 60))

            Box(
                modifier = Modifier
                    .background(
                        color = Color.White.copy(alpha = 0.7f),
                        shape = RoundedCornerShape(6.dp)
                    )
                    .align(Alignment.Start),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "+ Create New Room",
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                    style = Inter.copy(
                        fontSize = 10.sp,
                        color = Color(0xFFBC0100).copy(0.5f),
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }
    }
}
