package com.example.wordsapp.core.di

import com.example.wordsapp.history.data.remote.HistoryRequestService
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent :: class)
object RetrofitModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit{
        return Retrofit.Builder().baseUrl("http://114.29.236.110:4000").addConverterFactory(
            GsonConverterFactory.create()).build()
    }


}