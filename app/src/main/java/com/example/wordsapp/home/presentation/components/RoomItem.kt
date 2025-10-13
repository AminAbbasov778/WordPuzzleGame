package com.example.wordsapp.home.presentation.components

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.navigation.NavHostController
import com.example.wordsapp.R
import com.example.wordsapp.core.navigation.Routes
import com.example.wordsapp.core.presentation.enums.Difficulty
import com.example.wordsapp.core.presentation.enums.Language
import com.example.wordsapp.core.presentation.enums.Status
import com.example.wordsapp.home.presentation.intent.HomeIntents
import com.example.wordsapp.home.presentation.model.GameRouteUi
import com.example.wordsapp.home.presentation.model.JoinRoomUi
import com.example.wordsapp.home.presentation.model.RoomUi
import com.example.wordsapp.home.presentation.navigation.HomeNavigation
import com.example.wordsapp.ui.theme.Inknut40
import com.example.wordsapp.ui.theme.Inter

@SuppressLint("SuspiciousIndentation")
@Composable
fun RoomItem( room: RoomUi, onIntent: (HomeIntents) -> Unit,username: String,userUid: String) {

    


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color(0xFF2E3641), shape = RoundedCornerShape(10.dp))
            .border(
                width = 1.dp,
                color = Color(0xFFC5C5C4).copy(alpha = 0.2f),
                shape = RoundedCornerShape(10.dp)
            )
            .padding(horizontal = 15.dp, vertical = 11.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(
                            if (room.status == Status.WAITING) R.drawable.ic_watch
                            else if (room.status ==Status.GAME) R.drawable.ic_game_controller
                            else if (room.status == Status.FULL) R.drawable.ic_stash_user_group else R.drawable.ic_watch
                        ),
                        contentDescription = null,
                        tint = if (room.status == Status.WAITING || room.status ==Status.GAME) Color.Unspecified else Color(0xFFF6647F)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = room.roomName, style = Inter.copy(color = Color.White, fontSize = 18.sp))


                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(painter = painterResource(R.drawable.ic_stash_user_group), tint = Color.Unspecified,contentDescription = null)
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(text = "${room.currentPlayers}/${room.maxPlayers}", style = Inknut40.copy(color = Color(0xFFCFCFCF), fontSize = 12.sp))
                    Spacer(modifier = Modifier.width(10.dp))
                    Icon(painter = painterResource(R.drawable.ic_watch), contentDescription = null, tint = Color(0xFFAFB0B2))
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(text = room.createdAt ?: "", style = Inknut40.copy(color = Color(0xFFCFCFCF), fontSize = 12.sp))
                    Spacer(modifier = Modifier.width(10.dp))
                    Icon(painter = painterResource(R.drawable.ic_difficulty), contentDescription = null, tint = Color(0xFFAFB0B2))
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(text = if(room.difficulty == Difficulty.EASY) "Easy" else if(room.difficulty == Difficulty.MEDIUM) "Medium" else "Hard", style = Inknut40.copy(color = Color(0xFFCFCFCF), fontSize = 12.sp))
                    Spacer(modifier = Modifier.width(10.dp))
                    Icon(painter = painterResource(R.drawable.iconoir_language), contentDescription = null, tint = Color(0xFFAFB0B2))
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(text = if(room.language == Language.EN) "En" else if(room.language == Language.AZ) "Az" else "Tr", style = Inknut40.copy(color = Color(0xFFCFCFCF), fontSize = 12.sp))

                }
            }

            Box(
                modifier = Modifier
                    .background(
                        color = if (room.currentPlayers == room.maxPlayers || room.status == Status.GAME || room.isJoinClicked) Color(
                            0xFFC40100
                        ).copy(0.5f) else Color(0xFFC40100), shape = RoundedCornerShape(6.dp)
                    )
                    .padding(horizontal = 10.dp, vertical = 4.dp)
                    .clickable {
                        if (room.currentPlayers == room.maxPlayers) return@clickable
                         if(room.status == Status.GAME ) return@clickable
                        if (room.isJoinClicked) return@clickable
                        Log.e("username", "RoomItem:${userUid} ", )

                        onIntent(
                            HomeIntents.JoinRoom(
                                JoinRoomUi(
                                    roomId = room.roomId,
                                    userId = userUid,
                                    username = username,
                                    difficulty = if (room.difficulty == Difficulty.EASY) "easy" else if (room.difficulty == Difficulty.MEDIUM) "medium" else "hard",
                                    language = if (room.language == Language.EN) "en" else if (room.language == Language.AZ) "az" else "tr"
                                ),  GameRouteUi(
                                    roomId = room.roomId,
                                    roomName = room.roomName,
                                    status = if(room.status == Status.WAITING) "waiting" else if(room.status == Status.GAME) "game" else "full",
                                    maxPlayers = room.maxPlayers,
                                    username = username,
                                    userUid = userUid
                                    ,
                                    currentPlayers = room.currentPlayers,
                                    difficulty = if(room.difficulty == Difficulty.EASY) "easy" else if(room.difficulty == Difficulty.MEDIUM) "medium" else "hard",
                                    language = if(room.language == Language.EN) "en" else if(room.language == Language.AZ) "az" else "tr",
                                )
                            )
                        )




                    },
                contentAlignment = Alignment.Center
            ) {
                Text(text = if(room.currentPlayers == room.maxPlayers ) "Full"  else if (room.status == Status.GAME) "Playing" else if (room.isJoinClicked) "Joined"  else "Join", style = Inter.copy(color = if(room.currentPlayers == room.maxPlayers || room.status == Status.GAME || room.isJoinClicked) Color.White.copy(0.5f) else Color.White, fontSize = 12.sp))
            }
        }
    }
}