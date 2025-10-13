package com.example.wordsapp.core.di

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
        return Retrofit.Builder().baseUrl("http://172.20.10.179:4000").addConverterFactory(
            GsonConverterFactory.create()).build()
    }


}