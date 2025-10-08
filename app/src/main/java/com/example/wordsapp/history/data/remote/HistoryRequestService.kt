package com.example.wordsapp.history.data.remote

import com.example.wordsapp.history.data.models.Leaderboard.Leaderboard
import com.example.wordsapp.history.data.models.gamedetail.GameDetail
import com.example.wordsapp.history.data.models.history.GamesResponse
import com.example.wordsapp.history.data.models.user.User
import retrofit2.http.GET
import retrofit2.http.Path

interface HistoryRequestService {

    @GET("games")
    suspend fun getGames(): GamesResponse

    @GET("games/{gameId}")
    suspend fun getGameDetail(@Path("gameId") gameId: String): GameDetail

    @GET("leaderboard")
    suspend fun getLeaderboard(): Leaderboard

    @GET("users/{userId}")
    suspend fun getUser(@Path("userId") userId: String): User


}


