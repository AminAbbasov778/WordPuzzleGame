package com.example.wordsapp.home.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.wordsapp.R
import com.example.wordsapp.home.data.Room
import com.example.wordsapp.home.presentation.HomeIntents
import com.example.wordsapp.home.presentation.RoomUi
import com.example.wordsapp.ui.theme.Inknut40
import com.example.wordsapp.ui.theme.Inter


@Composable
fun RoomsBody(navController: NavHostController,
              rooms: List<RoomUi>,
              getRelativeTime: (Long?) -> String,
              modifier: Modifier = Modifier,
              isStatusGuideVisible: (Boolean) -> Unit,
              onIntent: (HomeIntents) -> Unit,username: String,userUid: String
) {
    Column(modifier = modifier.fillMaxWidth()) {


        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Available Rooms (${rooms.size})",
                style = Inknut40.copy(color = Color(0xFFCFCFCF), fontSize = 12.sp),
                modifier = Modifier.weight(1f)
            )
            Icon(
                painter = painterResource(R.drawable.ic_info),
                contentDescription = "info",
                tint = Color.Unspecified,
                modifier = Modifier.align(Alignment.CenterVertically).clickable{
                    isStatusGuideVisible(true)

                }
            )
        }

        Spacer(modifier = Modifier.height(19.dp))

        if (rooms.isNotEmpty()) {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(rooms) { room ->
                    RoomItem(navController,room,onIntent,username,userUid)
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
                    style = Inter.copy(color = Color(0xFFACAEB1), fontSize = 12.sp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
