package com.example.wordsapp.history.presentation.components

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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.wordsapp.R
import com.example.wordsapp.history.presentation.intent.HistoryIntent
import com.example.wordsapp.history.presentation.state.HistoryState
import com.example.wordsapp.ui.theme.Inter

@Composable
fun TopBar(state: HistoryState,onEvent : (HistoryIntent) -> Unit) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp


    Column {
        Icon(
            painter = painterResource(R.drawable.ic_back),
            tint = Color.Unspecified,
            contentDescription = "back", modifier = Modifier.clickable{
                onEvent(HistoryIntent.GoToHome)
            }
        )
        Spacer(modifier = Modifier.height(21.dp))




        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(listOf(Color(0xFFC40100), Color(0xFFA00100))),
                    shape = RoundedCornerShape(10.dp)
                )
        ) {


            Column(
                modifier = Modifier
                    .padding(vertical = screenHeight / 60)
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Total Score",
                    modifier = Modifier,
                    style = Inter.copy(
                        fontSize = 11.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Medium
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = if(state.user?.data?.user?.totalScore != null) state.user.data.user.totalScore.toString() else "0",
                    modifier = Modifier,
                    style = Inter.copy(
                        fontSize = 24.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.height(21.dp))

                Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween) {



                        Box(
                            modifier = Modifier.wrapContentHeight().width(screenWidth * 0.2f)

                                .background(
                                    color = Color(0xFFD9D9D9).copy(alpha = 0.1f),
                                    shape = RoundedCornerShape(8.dp)
                                ) .padding(start = 8.dp).padding(vertical = 8.dp)
                        ) {
                            Column {
                                Text(
                                    text = "Games",
                                    modifier = Modifier,
                                    style = Inter.copy(
                                        fontSize = 10.sp,
                                        color = Color.White.copy(0.8f),
                                        fontWeight = FontWeight.Medium
                                    )
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = if(state.user?.data?.user?.totalGames != null) state.user.data.user.totalGames.toString() else "0",
                                    modifier = Modifier,
                                    style = Inter.copy(
                                        fontSize = 16.sp,
                                        color = Color.White,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                )
                            }


                        }


                    Box(
                        modifier = Modifier.wrapContentHeight().width(screenWidth * 0.2f)

                            .background(
                                color = Color(0xFFD9D9D9).copy(alpha = 0.1f),
                                shape = RoundedCornerShape(8)
                            ) .padding(start = 8.dp).padding(vertical = 8.dp)
                    ) {
                        Column {
                            Text(
                                text = "Wins",
                                modifier = Modifier,
                                style = Inter.copy(
                                    fontSize = 10.sp,
                                    color = Color.White.copy(0.8f),
                                    fontWeight = FontWeight.Medium
                                )
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = if(state.user?.data?.user?.wins != null) state.user.data.user.wins.toString() else "0",
                                modifier = Modifier,
                                style = Inter.copy(
                                    fontSize = 16.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }


                    }

                    Box(
                        modifier = Modifier.wrapContentHeight().width(screenWidth * 0.2f)

                            .background(
                                color = Color(0xFFD9D9D9).copy(alpha = 0.1f),
                                shape = RoundedCornerShape(8)
                            ) .padding(start = 8.dp).padding(vertical = 8.dp)
                    ) {
                        Column {
                            Text(
                                text = "Loses",
                                modifier = Modifier,
                                style = Inter.copy(
                                    fontSize = 10.sp,
                                    color = Color.White.copy(0.8f),
                                    fontWeight = FontWeight.Medium
                                )
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = if(state.user?.data?.user?.losses != null) state.user.data.user.losses.toString() else "0",
                                modifier = Modifier,
                                style = Inter.copy(
                                    fontSize = 16.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }


                    }


                }



            }

        }


    }

}