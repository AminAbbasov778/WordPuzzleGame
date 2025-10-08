package com.example.wordsapp.home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import com.example.wordsapp.core.data.models.RoomState
import com.example.wordsapp.home.presentation.HomeIntents
import com.example.wordsapp.home.presentation.HomeState
import com.example.wordsapp.home.presentation.RoomUi
import com.example.wordsapp.home.presentation.Status
import com.example.wordsapp.home.presentation.StatusUi
import com.example.wordsapp.ui.theme.Inter


@Composable
fun RoomsBody(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    isStatusGuideVisible: (Boolean) -> Unit,
    onIntent: (HomeIntents) -> Unit, username: String, userUid: String,state : HomeState
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.height(21.dp))


        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(R.drawable.ic_door),
                    contentDescription = "door",
                    tint = Color.Unspecified,
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = state.filteredRooms.size.toString(),
                    style = Inter.copy(color = Color(0xFFCFCFCF), fontSize = 12.sp),
                    modifier = Modifier
                )
            }

            Spacer(modifier = Modifier.width(20.dp))


            Row(
                modifier = Modifier
                    .background(
                        color = Color(0xFF7D8899).copy(0.1f),
                        shape = RoundedCornerShape(50.dp)
                    )
                    .border(
                        width =1.dp,
                        color = Color(0xFF7D8899).copy(0.1f),
                        shape = RoundedCornerShape(50.dp)
                    )
                    .weight(1f),horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                state.status.forEachIndexed { index,item->
                    Box(
                        modifier = Modifier
                            .padding(start = 2.dp, bottom = 2.dp, top = 2.dp)
                            .background(
                                color = if (item.isSelected) Color.White else Color.Transparent,
                                shape = RoundedCornerShape(50.dp)
                            ).weight(1f).clickable {
                                onIntent(HomeIntents.ChangeCurrentTab(if(item.status == Status.WAITING) Status.WAITING  else if(item.status == Status.GAME) Status.GAME else Status.FULL))
                            }, contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = item.statusName ,
                            style = Inter.copy(
                                color = if (item.isSelected) Color.Black else Color.White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold
                            ),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }


                }
            }

            Spacer(modifier = Modifier.width(20.dp))





            Icon(
                painter = painterResource(R.drawable.ic_info),
                contentDescription = "info",
                tint = Color.Unspecified,
                modifier = Modifier
                    .clickable {
                        isStatusGuideVisible(true)

                    }
            )
        }

        Spacer(modifier = Modifier.height(19.dp))

        if (state.filteredRooms.isNotEmpty()) {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(state.filteredRooms) { room ->
                    RoomItem(navController, room, onIntent, username, userUid)
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
