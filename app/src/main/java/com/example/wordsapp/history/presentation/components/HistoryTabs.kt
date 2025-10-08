package com.example.wordsapp.history.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wordsapp.history.presentation.HistoryIntent
import com.example.wordsapp.history.presentation.HistoryState
import com.example.wordsapp.history.presentation.HistoryTabUi
import com.example.wordsapp.history.presentation.HistoryTabs
import com.example.wordsapp.home.presentation.StatusUi
import com.example.wordsapp.ui.theme.Inter

@Composable
fun HistoryTabs(state : HistoryState, onEvent : (HistoryIntent) -> Unit) {

    val list = listOf<HistoryTabUi>(HistoryTabUi("History",state.currentTab == HistoryTabs.HISTORY,HistoryTabs.HISTORY), HistoryTabUi("LeaderBoard",state.currentTab == HistoryTabs.LEADERBOARD,HistoryTabs.LEADERBOARD))

    Column(modifier = Modifier.padding(horizontal = 60.dp).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally){
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
                ), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            list.forEach { item ->
                Box(
                    modifier = Modifier
                        .padding(start = 2.dp, bottom = 2.dp, top = 2.dp)
                        .background(
                            color = if (item.isSelected) Color.White else Color.Transparent,
                            shape = RoundedCornerShape(50.dp)
                        ).weight(1f).clickable{
                            onEvent(HistoryIntent.ChangeTab(if(item.statusName == "History") HistoryTabs.HISTORY else HistoryTabs.LEADERBOARD))
                        }, contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = item.statusName,
                        style = Inter.copy(
                            color = if (item.isSelected) Color.Black else Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold
                        ),
                        modifier = Modifier.padding(horizontal = 18.dp, vertical = 8.dp)
                    )
                }

            }
        }

    }



}