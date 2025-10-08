package com.example.wordsapp.home.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wordsapp.R
import com.example.wordsapp.history.presentation.HistoryState
import com.example.wordsapp.ui.theme.Inter


@Composable
fun LeaderboardItem(state: HistoryState) {
    val sortedList = state.leaderboard.sortedByDescending { it.rank }.reversed()
    val myRank = sortedList.find { it.userId == state.user?.data?.user?.userId }

    Column(modifier = Modifier.fillMaxWidth()) {


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xFF353C4B), shape = RoundedCornerShape(10.dp))
        ) {
            Image(
                painter = painterResource(R.drawable.pattern),
                contentDescription = null,
                modifier = Modifier,
                contentScale = ContentScale.Fit
            )
            Column(modifier = Modifier.fillMaxWidth()) {
                Spacer(
                    modifier = Modifier
                        .height(12.dp)
                )
                Text(
                    text = "Your Ranking",
                    style = Inter.copy(
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    ),
                    modifier = Modifier.padding(start = 16.dp)
                )

                Spacer(
                    modifier = Modifier
                        .height(18.dp)
                )


                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 14.dp)
                        .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if(myRank?.rank == null)  "-" else myRank.rank.toString(),
                            style = Inter.copy(
                                color = Color.White,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .background(
                                    color = Color(0xFF383D45),
                                    shape = RoundedCornerShape(18.dp)
                                )
                                .border(
                                    width = 1.dp,
                                    color = Color.White,
                                    shape = RoundedCornerShape(18.dp)
                                )
                        ) {

                        }

                        Spacer(modifier = Modifier.width(9.dp))


                        Column {
                            Text(
                                text = myRank?.username ?: "",
                                style = Inter.copy(
                                    color = Color.White,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium
                                ),
                                maxLines = 1,
                                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                                modifier = Modifier.padding(end = 60.dp)
                            )
                            Text(
                                text = myRank?.userId ?: "",
                                style = Inter.copy(
                                    color = Color(0xFF8591A3).copy(0.8f),
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold
                                ),
                                maxLines = 1,
                                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                                modifier = Modifier.padding(end = 60.dp)

                            )

                        }


                    }

                    Text(
                        text = if(myRank?.totalScore == null)  "0" else myRank.totalScore.toString(),
                        style = Inter.copy(
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        ),
                        modifier = Modifier
                    )


                }
            }


        }

        Spacer(
            modifier = Modifier
                .height(8.dp)
        )

        if(state.leaderboard.isEmpty()) return  EmptyLeaderBoard()



        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            itemsIndexed(sortedList) { index, item ->


                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color(0xFF353C4B).copy(0.4f),
                            shape = RoundedCornerShape(10.dp)
                        )
                ) {

                    Row(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 14.dp)
                            .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = item.rank.toString(),
                            style = Inter.copy(
                                color = Color.White,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .background(
                                    color = Color(0xFF383D45),
                                    shape = RoundedCornerShape(18.dp)
                                )
                                .border(
                                    width = 1.dp,
                                    color = Color.White,
                                    shape = RoundedCornerShape(18.dp)
                                )
                        ) {

                        }

                        Spacer(modifier = Modifier.width(9.dp))


                        Text(
                            text = item.username,
                            style = Inter.copy(
                                color = Color.White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            ),
                            maxLines = 1,
                            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                            modifier = Modifier.padding(end = 60.dp)
                        )


                    }

                    Text(
                        text = item.totalScore.toString(),
                        style = Inter.copy(
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        ),
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .align(
                                Alignment.CenterEnd
                            )
                    )


                }
                Spacer(
                    modifier = Modifier
                        .height(8.dp)
                )
            }


        }

    }


}