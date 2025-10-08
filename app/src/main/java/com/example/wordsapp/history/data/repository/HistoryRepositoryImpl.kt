package com.example.wordsapp.history.data.repository

import com.example.wordsapp.history.data.models.Leaderboard.Leaderboard
import com.example.wordsapp.history.data.models.gamedetail.GameDetail
import com.example.wordsapp.history.data.models.history.GamesResponse
import com.example.wordsapp.history.data.models.user.User
import com.example.wordsapp.history.data.remote.HistoryRequestService
import com.example.wordsapp.history.domain.repository.HistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val service: HistoryRequestService
) : HistoryRepository {

    override fun getGames(): Flow<Result<GamesResponse>> = flow {
        try {
            val response = service.getGames()
            emit(Result.success(response))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    override fun getGameDetail(gameId: String): Flow<Result<GameDetail>> = flow {
        try {
            val response = service.getGameDetail(gameId)
            emit(Result.success(response))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    override fun getLeaderboard(): Flow<Result<Leaderboard>> = flow {
        try {
            val response = service.getLeaderboard()
            emit(Result.success(response))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    override fun getUser(userId: String): Flow<Result<User>> = flow {
        try {
            val response = service.getUser(userId)
            emit(Result.success(response))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }



}
