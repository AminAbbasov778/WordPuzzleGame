package com.example.wordsapp.home.presentation.components

import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wordsapp.R
import com.example.wordsapp.home.presentation.model.GuideUi
import com.example.wordsapp.ui.theme.Inter



@Composable
fun StatusGuideFooter(modifier: Modifier = Modifier) {
    val list = arrayListOf(
        GuideUi(R.drawable.ic_watch, "Waiting", "Room is waiting for players to join"),
        GuideUi(R.drawable.ic_game_controller, "Playing", "Game is currently in progress"),
        GuideUi(R.drawable.ic_stash_user_group, "Full", "Room has reached maximum capacity")
    )


    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(shape = RoundedCornerShape(16.dp), color = Color(0xFF2E3740))
    ) {
        Column(modifier = Modifier
            .padding(top = 12.dp)
            .fillMaxWidth()) {
            Box(modifier = Modifier
                .fillMaxWidth(0.1f)
                .height(3.dp)
                .background(color = Color(0xFFCFCFCF).copy(0.8f), shape = RoundedCornerShape(9.dp))
                .align(Alignment.CenterHorizontally))
            Spacer(modifier = Modifier.height(17.dp))

            Text(
                text = "Room Status Guide",
                style = Inter.copy(color = Color(0xFFCFCFCF).copy(0.8f), fontSize = 14.sp, letterSpacing = 0.7.sp),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 34.dp, vertical = 24.dp)
                    .fillMaxWidth()
            ) {
                items(list) { item ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(
                                if (item.title == "Waiting") R.drawable.ic_watch
                                else if (item.title == "Playing") R.drawable.ic_game_controller
                                else R.drawable.ic_stash_user_group
                            ),
                            contentDescription = null,
                            tint = if (item.title == "Full") Color(0xFFF6647F) else Color.Unspecified
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(text = item.title, letterSpacing = 0.7.sp, style = Inter.copy(color = Color(0xFF42D591), fontSize = 12.sp))
                            Spacer(modifier = Modifier.height(3.dp))

                            Text(text = item.description, letterSpacing = (0.7).sp,style = Inter.copy(color = Color(0xFFCFCFCF), fontSize = 10.sp))
                        }
                    }
                    if(item != list.last()){
                        Spacer(modifier = Modifier.height(21.dp))

                    }
                }
            }
        }
    }
}

