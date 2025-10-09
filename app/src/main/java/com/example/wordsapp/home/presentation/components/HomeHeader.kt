package com.example.wordsapp.home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.wordsapp.R
import com.example.wordsapp.core.navigation.Routes
import com.example.wordsapp.history.presentation.model.HistoryRouteUi
import com.example.wordsapp.home.presentation.intent.HomeIntents
import com.example.wordsapp.home.presentation.state.HomeState
import com.example.wordsapp.ui.theme.Inter

@Composable
fun HomeHeader(state: HomeState, username : String, onIntent: (HomeIntents) -> Unit) {



    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row(modifier = Modifier.weight(1f),verticalAlignment = Alignment.CenterVertically){
            Box(modifier = Modifier.background(brush = Brush.linearGradient(listOf(Color(0xFFC40100), Color(0xFFA00100))) , shape = RoundedCornerShape(8.dp)),contentAlignment = Alignment.Center){
                Text(text = "H",modifier = Modifier.padding(horizontal = 11.dp, vertical = 12.dp),style = Inter.copy(fontSize = 20.sp, color = Color.White, fontWeight = FontWeight.Bold))


            }
            Spacer(modifier = Modifier.width(13.dp))


            Column(modifier = Modifier) {
                Text(
                    text = "Hanged",
                    style = Inter.copy(color = Color(0xFFC4C4C4), fontSize = 20.sp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Welcome, $username",
                    maxLines = 1, overflow = TextOverflow.Ellipsis,
                    style = Inter.copy(color = Color(0xFFC4C4C4), fontSize = 11.sp)
                )
            }
        }
        Spacer(modifier = Modifier.width(20.dp))

        Row {
            Box(modifier = Modifier.background(color = Color(0xFF7D8899).copy(0.1f) , shape = RoundedCornerShape(6.dp))){
                Icon(
                    painter = painterResource(R.drawable.ic_history),
                    contentDescription = "logout",
                    tint = Color.Unspecified,
                    modifier = Modifier.padding(8.dp).clickable{
                        state.userUid?.let {
                            onIntent(HomeIntents.GoToHistoryScreen)
                        }
                    }
                )
            }

            Spacer(modifier =  Modifier.width(8.dp))

            Box(modifier = Modifier.background(color = Color(0xFF7D8899).copy(0.1f)  , shape = RoundedCornerShape((6.dp)))){
                Icon(
                    painter = painterResource(R.drawable.ic_leave),
                    contentDescription = "logout",
                    tint = Color.Unspecified,
                    modifier = Modifier.padding(8.dp).clickable{
                        onIntent(HomeIntents.SignOut)
                    }
                )
            }


        }



    }
}