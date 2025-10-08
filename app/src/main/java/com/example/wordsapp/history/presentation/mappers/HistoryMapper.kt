package com.example.wordsapp.history.presentation.mappers

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.wordsapp.core.utils.formatDate
import com.example.wordsapp.history.data.models.Leaderboard.LeaderboardX
import com.example.wordsapp.history.data.models.gamedetail.GameDetail
import com.example.wordsapp.history.data.models.history.Game
import com.example.wordsapp.history.data.models.history.Player
import com.example.wordsapp.history.data.models.history.Winner
import com.example.wordsapp.history.data.models.user.User
import com.example.wordsapp.history.presentation.gamedetail.GameDetailPlayerUi
import com.example.wordsapp.history.presentation.gamedetail.GameDetailUi
import com.example.wordsapp.history.presentation.gamedetail.GameDetailWinnerUi
import com.example.wordsapp.history.presentation.history.GameUi
import com.example.wordsapp.history.presentation.history.PlayerUi
import com.example.wordsapp.history.presentation.history.WinnerUi
import com.example.wordsapp.history.presentation.leaderboard.LeaderboardUi


@RequiresApi(Build.VERSION_CODES.O)
fun Game.toUi(): GameUi {
    return GameUi(
        createdAt = formatDate(this.createdAt),
        difficulty = this.difficulty,
        id = this.id,
        language = this.language,
        players = this.players.map { it.toUi() },
        roomId = this.roomId,
        winner = this.winner.toUi(),
        word = this.word
    )
}

fun Player.toUi(): PlayerUi {
    return PlayerUi(

        eliminated = this.eliminated,
        id = this.id,
        name = this.name,
        score = this.score
    )
}

fun Winner.toUi(): WinnerUi {
    return WinnerUi(
        score = this.score,
        id = this.id,
        name = this.name,
    )
}


fun LeaderboardX.toUi(): LeaderboardUi {
    return LeaderboardUi(
        rank = this.rank,
        totalScore = this.totalScore,
        userId = this.userId,
        username = this.username
    )
}


@RequiresApi(Build.VERSION_CODES.O)
fun GameDetail.toUi(): GameDetailUi {
    return GameDetailUi(
        createdAt = formatDate(this.data.game.createdAt),

        difficulty = this.data.game.difficulty,

        id = this.data.game.id,

        language = this.data.game.language,

        players = this.data.game.players.map { it.toUi() },


        roomId = this.data.game.roomId,

        winner = this.data.game.winner.toUi(),


        word = this.data.game.word,

        roomName = this.data.game.roomName,
        isWordVisible = false
    )
}

fun com.example.wordsapp.history.data.models.gamedetail.Player.toUi(): GameDetailPlayerUi {
    return GameDetailPlayerUi(
        eliminated = this.eliminated,
        id = this.id,
        name = this.name,
        score = this.score
    )
}

fun com.example.wordsapp.history.data.models.gamedetail.Winner.toUi(): GameDetailWinnerUi {
    return GameDetailWinnerUi(
        id = this.id,
        name = this.name,
        score = this.score
    )
}



