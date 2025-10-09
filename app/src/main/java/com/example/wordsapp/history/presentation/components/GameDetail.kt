package com.example.wordsapp.history.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wordsapp.R
import com.example.wordsapp.history.presentation.gamedetail.GameDetailUi
import com.example.wordsapp.history.presentation.intent.HistoryIntent
import com.example.wordsapp.ui.theme.Inter
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameDetail(gameDetail: GameDetailUi, onEvent: (HistoryIntent) -> Unit) {

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp






    ModalBottomSheet(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding(),
        onDismissRequest = {
            onEvent(HistoryIntent.ChangeWordVisibilityInDetail)
            onEvent(HistoryIntent.ChangeGameDetailVisibility)
            scope.launch { sheetState.hide() }


        },
        sheetState = sheetState,
        containerColor = Color(0xFF2A303C),
        shape = RoundedCornerShape(16.dp),
        tonalElevation = 8.dp,
        dragHandle = {


        }
    ) {

        val active = gameDetail.players.filter { !it.eliminated }
        val eliminated = gameDetail.players.filter { it.eliminated }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(screenHeight * 0.65f)
        ) {

            Box(
                modifier = Modifier
                    .padding(top = 18.dp)
                    .height(4.dp)
                    .width(screenWidth * 0.13f)
                    .background(
                        color = Color(0xFFAEB0B2),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .align(
                        Alignment.TopCenter
                    )
            )
            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 14.dp)
                    .fillMaxSize()
            ) {


                item {
                    Column(modifier = Modifier.fillMaxWidth()) {

                        Spacer(
                            modifier = Modifier
                                .height(28.dp)

                        )

                        Text(
                            text = gameDetail.roomName,
                            style = Inter.copy(
                                color = Color(0xFFD9D9D9),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                        Spacer(
                            modifier = Modifier
                                .height(2.dp)

                        )

                        Text(
                            text = gameDetail.createdAt,
                            style = Inter.copy(
                                color = Color(0xFFAEB0B2),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                        Spacer(
                            modifier = Modifier
                                .height(14.dp)

                        )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    color = Color(0xFFACAEB1).copy(0.05f),
                                    shape = RoundedCornerShape(16.dp)
                                )
                        ,contentAlignment = Alignment.Center
                        ) {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                Spacer(
                                    modifier = Modifier
                                        .height(14.dp)

                                )
                                Row(
                                    modifier = Modifier.align(Alignment.CenterHorizontally),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    if (gameDetail.isWordVisible) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text(
                                                text = gameDetail.word,
                                                style = Inter.copy(
                                                    color = Color.White,
                                                    fontSize = 24.sp,
                                                    fontWeight = FontWeight.SemiBold
                                                )
                                            )
                                            Spacer(modifier = Modifier.width(9.dp))
                                            Icon(
                                                painter = painterResource(if (!gameDetail.isWordVisible) R.drawable.ic_invisible_small else R.drawable.ic_visible_small),
                                                contentDescription = null,
                                                tint = Color.Unspecified,
                                                modifier = Modifier
                                                    .align(
                                                        Alignment.CenterVertically
                                                    )
                                                    .clickable {
                                                        onEvent(HistoryIntent.ChangeWordVisibilityInDetail)

                                                    })
                                        }
                                    } else {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            LazyRow() {
                                                items(gameDetail.word.length) {
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
                                                painter = painterResource(if (!gameDetail.isWordVisible) R.drawable.ic_invisible_small else R.drawable.ic_visible_small),
                                                contentDescription = null,
                                                tint = Color.Unspecified,
                                                modifier = Modifier.padding(bottom = 8.dp).align(Alignment.CenterVertically)
                                                    .clickable {
                                                        onEvent(HistoryIntent.ChangeWordVisibilityInDetail)

                                                    })
                                        }
                                    }


                                }

                                Spacer(
                                    modifier = Modifier
                                        .height(4.dp)

                                )
                                Text(
                                    text = "${gameDetail.language} | ${gameDetail.difficulty}",
                                    style = Inter.copy(
                                        color = Color(0xFFAEB0B2),
                                        fontSize = 7.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                )

                                Spacer(
                                    modifier = Modifier
                                        .height(20.dp)


                                )
                                Row(
                                    modifier = Modifier
                                        .padding(horizontal = 34.dp)
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(
                                            text = "Your score",
                                            style = Inter.copy(
                                                color = Color(0xFFAEB0B2),
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.Medium
                                            )
                                        )
                                        Spacer(
                                            modifier = Modifier
                                                .height(4.dp)


                                        )
                                        Text(
                                            text = gameDetail.winner.score.toString(),
                                            style = Inter.copy(
                                                color = Color(0xFFD9D9D9),
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.Medium
                                            )
                                        )

                                    }

                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(
                                            text = "Player",
                                            style = Inter.copy(
                                                color = Color(0xFFAEB0B2),
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.Medium
                                            )
                                        )
                                        Spacer(
                                            modifier = Modifier
                                                .height(4.dp)


                                        )
                                        Text(
                                            text = gameDetail.players.size.toString(),
                                            style = Inter.copy(
                                                color = Color(0xFFD9D9D9),
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.Medium
                                            )
                                        )

                                    }
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(
                                            text = "Wrong",
                                            style = Inter.copy(
                                                color = Color(0xFFAEB0B2),
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.Medium
                                            )
                                        )
                                        Spacer(
                                            modifier = Modifier
                                                .height(4.dp)


                                        )
                                        Text(
                                            text = "${gameDetail.players.size}",
                                            style = Inter.copy(
                                                color = Color(0xFFD9D9D9),
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.Medium
                                            )
                                        )

                                    }


                                }

                                Spacer(
                                    modifier = Modifier
                                        .height(14.dp)


                                )


                            }
                        }


                    }

                    Spacer(
                        modifier = Modifier
                            .height(12.dp)


                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = Color(0xFFD4D413).copy(0.1f),
                                shape = RoundedCornerShape(16.dp)
                            )
                            .border(
                                width = 0.5.dp,
                                color = Color(0xFFD4D413).copy(0.5f),
                                shape = RoundedCornerShape(16.dp)
                            )
                    ) {
                        Column(
                            horizontalAlignment = Alignment.Start,
                            modifier = Modifier.padding(start = 9.dp, top = 8.dp, bottom = 8.dp)
                        ) {
                            Text(
                                text = "Winner",
                                style = Inter.copy(
                                    color = Color(0xFFAEB0B2),
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            )
                            Spacer(
                                modifier = Modifier
                                    .height(6.dp)


                            )
                            Text(
                                text = gameDetail.winner.name,
                                style = Inter.copy(
                                    color = Color(0xFFD9D9D9),
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )

                        }

                        Text(
                            text = "${gameDetail.winner.score.toString()} points",
                            style = Inter.copy(
                                color = Color(0xFFD4D413),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.SemiBold
                            ),
                            modifier = Modifier
                                .padding(end = 9.dp, bottom = 8.dp)
                                .align(Alignment.BottomEnd)
                        )

                    }


                    Spacer(
                        modifier = Modifier
                            .height(18.dp)


                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(5.dp)
                                .background(
                                    color = Color(0xFF3EB481),
                                    shape = RoundedCornerShape(3.dp)
                                )
                        )

                        Spacer(
                            modifier = Modifier
                                .width(4.dp)


                        )

                        Text(
                            text = "Active Players (${active.size})",
                            style = Inter.copy(
                                color = Color(0xFFAEB0B2),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        )

                    }
                    Spacer(
                        modifier = Modifier
                            .height(12.dp)


                    )
                }





                items(active) { player ->


                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = Color(0xFF3EB481).copy(0.1f),
                                shape = RoundedCornerShape(16.dp)
                            )
                            .border(
                                width = 0.5.dp,
                                color = Color(0xFF3EB481).copy(0.5f),
                                shape = RoundedCornerShape(16.dp)
                            )
                    ) {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Spacer(
                                modifier = Modifier.width(8.dp)
                            )
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .background(
                                        color = Color(0xFF3EB481).copy(0.5f),
                                        shape = RoundedCornerShape(30.dp)
                                    ), contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = player.name.first().toString(),
                                    style = Inter.copy(
                                        color = Color(0xFFD9D9D9),
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Medium
                                    ), modifier = Modifier
                                )
                            }

                            Spacer(
                                modifier = Modifier
                                    .width(9.dp)


                            )

                            Column(
                                horizontalAlignment = Alignment.Start,
                                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                            ) {
                                Text(
                                    text = player.name,
                                    style = Inter.copy(
                                        color = Color(0xFFAEB0B2),
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                )
                                Spacer(
                                    modifier = Modifier
                                        .height(2.dp)


                                )
                                if(player.id == gameDetail.winner.id)
                                {
                                    Row(verticalAlignment = Alignment.CenterVertically) {

                                        Icon(
                                            painter = painterResource(R.drawable.ic_crown),
                                            tint = Color.Unspecified, modifier = Modifier.size(8.dp),
                                            contentDescription = null
                                        )
                                        Spacer(
                                            modifier = Modifier
                                                .width(2.dp)


                                        )
                                        Text(
                                            text = "Winner",
                                            style = Inter.copy(
                                                color = Color(0xFFD4D413),
                                                fontSize = 8.sp,
                                                fontWeight = FontWeight.SemiBold
                                            )
                                        )

                                    }
                                }


                            }


                        }



                        Column(
                            modifier = Modifier
                                .padding(end = 11.dp)
                                .align(Alignment.CenterEnd), horizontalAlignment = Alignment.End

                        ) {
                            Text(
                                text = player.score.toString(),
                                style = Inter.copy(
                                    color = Color(0xFFD9D9D9),
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium
                                ), modifier = Modifier
                            )


                            Spacer(
                                modifier = Modifier
                                    .height(2.dp)


                            )

                            Text(
                                text = "points",
                                style = Inter.copy(
                                    color = Color(0xFFAEB0B2),
                                    fontSize = 8.sp,
                                    fontWeight = FontWeight.SemiBold
                                ), modifier = Modifier
                            )

                        }


                    }

                    Spacer(modifier = Modifier.height(7.dp))


                }
                item {
                    Spacer(
                        modifier = Modifier
                            .height(18.dp)


                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(5.dp)
                                .background(
                                    color = Color(0xFFC40100),
                                    shape = RoundedCornerShape(3.dp)
                                )
                        )

                        Spacer(
                            modifier = Modifier
                                .width(4.dp)


                        )

                        Text(
                            text = "Eliminated Players (${eliminated.size})",
                            style = Inter.copy(
                                color = Color(0xFFAEB0B2),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        )

                    }
                    Spacer(
                        modifier = Modifier
                            .height(12.dp)


                    )


                }
                items(eliminated) {player ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = Color(0xFFC40100).copy(0.1f),
                                shape = RoundedCornerShape(16.dp)
                            )
                            .border(
                                width = 0.5.dp,
                                color = Color(0xFFC40100).copy(0.5f),
                                shape = RoundedCornerShape(16.dp)
                            )
                    ) {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Spacer(
                                modifier = Modifier.width(8.dp)
                            )
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .background(
                                        color = Color(0xFFC40100).copy(0.5f),
                                        shape = RoundedCornerShape(30.dp)
                                    ), contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = player.name.first().toString(),
                                    style = Inter.copy(
                                        color = Color(0xFFD9D9D9),
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Medium
                                    ), modifier = Modifier
                                )
                            }

                            Spacer(
                                modifier = Modifier
                                    .width(9.dp)


                            )

                            Column(
                                horizontalAlignment = Alignment.Start,
                                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                            ) {
                                Text(
                                    text = player.name,
                                    style = Inter.copy(
                                        color = Color(0xFFAEB0B2),
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                )
                                Spacer(
                                    modifier = Modifier
                                        .height(6.dp)


                                )

                                Text(
                                    text = "Eliminated",
                                    style = Inter.copy(
                                        color = Color(0xFC40100),
                                        fontSize = 8.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                )


                            }


                        }
                        Column(
                            modifier = Modifier
                                .padding(end = 9.dp, bottom = 8.dp)
                                .align(Alignment.CenterEnd), horizontalAlignment = Alignment.End

                        ) {
                            Text(
                                text = player.score.toString(),
                                style = Inter.copy(
                                    color = Color(0xFFD9D9D9),
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium
                                ), modifier = Modifier
                            )


                            Spacer(
                                modifier = Modifier
                                    .height(2.dp)


                            )

                            Text(
                                text = "points",
                                style = Inter.copy(
                                    color = Color(0xFFAEB0B2),
                                    fontSize = 8.sp,
                                    fontWeight = FontWeight.SemiBold
                                ), modifier = Modifier
                            )

                        }


                    }

                    Spacer(modifier = Modifier.height(7.dp))


                }


            }


        }

        Spacer(modifier = Modifier.height(7.dp))



    }




}


