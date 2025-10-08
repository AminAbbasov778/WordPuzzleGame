package com.example.wordsapp.history.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wordsapp.R
import com.example.wordsapp.history.presentation.HistoryIntent
import com.example.wordsapp.history.presentation.history.GameUi
import com.example.wordsapp.home.presentation.components.EmptyHistory
import com.example.wordsapp.ui.theme.Inter

@Composable
fun HistoryItem(gameHistory: List<GameUi>, userId: String, onEvent: (HistoryIntent) -> Unit) {


    if(gameHistory.isEmpty()) return EmptyHistory()
    LazyColumn(
        modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(gameHistory) { item ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(116.dp)
                    .background(
                        color = Color(0xFF2A303C),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .clickable {

                        onEvent(HistoryIntent.GameCLicked(item.id))
                    }
            ) {
                Box(
                    modifier = Modifier
                        .width(11.dp)
                        .fillMaxHeight()
                        .background(
                            color = if (item.winner.id == userId) Color(0xFF3EB481) else Color(
                                0xFFC40100
                            ),
                            shape = RoundedCornerShape(topStart = 10.dp, bottomStart = 10.dp)
                        )
                        .align(Alignment.TopStart)
                )

                Icon(
                    painter = painterResource(if (item.winner.id == userId) R.drawable.ic_victory else R.drawable.ic_defeated),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(4.dp)
                )


                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 22.dp, top = 11.dp, bottom = 11.dp, end = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f)
                    ) {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (item.isWordVisible) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = item.word,
                                        style = Inter.copy(
                                            color = Color.White,
                                            fontSize = 24.sp,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    )
                                    Spacer(modifier = Modifier.width(9.dp))
                                    Icon(
                                        painter = painterResource(if (!item.isWordVisible) R.drawable.ic_invisible_small else R.drawable.ic_visible_small),
                                        contentDescription = null,
                                        tint = Color.Unspecified,
                                        modifier = Modifier
                                            .align(
                                                Alignment.CenterVertically
                                            )
                                            .clickable {
                                                onEvent(HistoryIntent.ChangeWordVisibility(item.id))

                                            })
                                }
                            } else {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    LazyRow() {
                                        items(item.word.length) {
                                            Text(
                                                text = "*", style = Inter.copy(
                                                    color = Color.White,
                                                    fontSize = 24.sp,
                                                    fontWeight = FontWeight.SemiBold
                                                )
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.width(9.dp))
                                    Icon(
                                        painter = painterResource(if (!item.isWordVisible) R.drawable.ic_invisible_small else R.drawable.ic_visible_small),
                                        contentDescription = null,
                                        tint = Color.Unspecified,
                                        modifier = Modifier.padding(bottom = 8.dp).align(Alignment.CenterVertically)
                                            .clickable {
                                                onEvent(HistoryIntent.ChangeWordVisibility(item.id))

                                            })
                                }
                            }


                        }



                        Text(
                            text = item.roomId,
                            style = Inter.copy(
                                color = Color(0xFFAEB0B2),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Normal
                            )
                        )

                        Spacer(modifier = Modifier.height(11.dp))

                        Row {
                            Column {
                                InfoRow(
                                    icon = R.drawable.ic_stash_user_group,
                                    text = item.players.size.toString()
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                InfoRow(
                                    icon = R.drawable.ic_difficulty,
                                    text = item.difficulty.toString()
                                )
                            }

                            Spacer(modifier = Modifier.width(17.dp))

                            Column {
                                InfoRow(
                                    icon = R.drawable.iconoir_language,
                                    text = item.language
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                InfoRow(
                                    icon = R.drawable.ic_calendar,
                                    text = item.createdAt
                                )
                            }
                        }
                    }

                    Column(
                        modifier = Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.End,
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = if (item.winner.score < 0) "${item.winner.score}" else "+${item.winner.score}",
                                style = Inter.copy(
                                    color = Color(0xFFD9D9D9),
                                    fontSize = 22.sp
                                )
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Icon(
                                painter = painterResource(R.drawable.ic_forward),
                                contentDescription = null,
                                tint = Color.Unspecified
                            )
                        }

                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy((-6).dp),
                            reverseLayout = true,
                            modifier = Modifier.padding(top = 4.dp)
                        ) {
                            itemsIndexed(item.players) { index, participant ->
                                Box(
                                    modifier = Modifier
                                        .size(20.dp)
                                        .background(
                                            color = Color(0xFFD9D9D9),
                                            shape = RoundedCornerShape(10.dp)
                                        )
                                        .border(
                                            width = 1.5.dp,
                                            color = Color(0xFF2A303C),
                                            shape = RoundedCornerShape(10.dp)
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = participant.name.first().uppercase(),
                                        style = Inter.copy(
                                            color = Color(0xFF2A303C),
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    )
                                }
                            }
                        }
                    }

                }
                Spacer(modifier = Modifier.height(8.dp))

            }
        }


    }
}

@Composable
private fun InfoRow(icon: Int, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = painterResource(icon),
            tint = Color.Unspecified,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            style = Inter.copy(
                color = Color(0xFFCFCFCF).copy(0.8f),
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal
            )
        )
    }
}