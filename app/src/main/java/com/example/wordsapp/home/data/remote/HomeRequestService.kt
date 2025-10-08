package com.example.wordsapp.home.data.remote

import com.example.wordsapp.home.data.module.room.Room
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeRequestService {
    @GET("rooms")
    suspend fun getRooms(@Query("status") status: Int): Room
}