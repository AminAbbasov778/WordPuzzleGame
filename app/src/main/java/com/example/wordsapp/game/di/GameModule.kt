package com.example.wordsapp.game.di

import com.example.wordsapp.core.network.SocketHandler
import com.example.wordsapp.game.data.repository.GameRepositoryImpl
import com.example.wordsapp.game.domain.repository.GameRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent :: class)
object GameModule {

    @Provides
    @Singleton
          fun provideGameRepository(socketHandler: SocketHandler): GameRepository {
        return GameRepositoryImpl(socketHandler)

    }





}