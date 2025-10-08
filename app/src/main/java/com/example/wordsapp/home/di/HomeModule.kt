package com.example.wordsapp.home.di

import com.example.wordsapp.home.data.remote.HomeRequestService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeModule {
    @Provides
    @Singleton
    fun provideHomeRequestService(retrofit: Retrofit): HomeRequestService {
        return retrofit.create(HomeRequestService::class.java)
    }
}


