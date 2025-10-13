package com.example.wordsapp.home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.wordsapp.R
import com.example.wordsapp.core.presentation.enums.Status
import com.example.wordsapp.home.presentation.intent.HomeIntents
import com.example.wordsapp.home.presentation.state.HomeState
import com.example.wordsapp.ui.theme.Inter


@Composable
fun RoomsBody(
    modifier: Modifier = Modifier,

    onIntent: (HomeIntents) -> Unit, username: String, userUid: String,state : HomeState
) {
    Column(modifier = modifier.fillMaxWidth()) {


        Spacer(modifier = Modifier.height(19.dp))

        if (state.filteredRooms.isNotEmpty()) {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(state.filteredRooms, key = { it.roomId }) { room ->
                    RoomItem( room, onIntent, username, userUid)
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        } else {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_log),
                    contentDescription = "log",
                    tint = Color.Unspecified,
                    modifier = Modifier.padding(top = 200.dp)
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "The gallows are ready, \u2028but there’s no room…",
                    style = Inter.copy(color = Color(0xFFACAEB1).copy(0.8f), fontSize = 12.sp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
