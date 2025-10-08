package com.example.wordsapp.history.domain.repository

import com.example.wordsapp.history.data.models.Leaderboard.Leaderboard
import com.example.wordsapp.history.data.models.gamedetail.GameDetail
import com.example.wordsapp.history.data.models.history.GamesResponse
import com.example.wordsapp.history.data.models.user.User
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {

    fun getGames(): Flow<Result<GamesResponse>>
    fun getGameDetail(gameId: String): Flow<Result<GameDetail>>
    fun getLeaderboard(): Flow<Result<Leaderboard>>
    fun getUser(userId: String): Flow<Result<User>>

}