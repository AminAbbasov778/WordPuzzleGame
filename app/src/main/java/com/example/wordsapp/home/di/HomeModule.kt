package com.example.wordsapp.home.di

import com.example.wordsapp.core.network.SocketHandler
import com.example.wordsapp.home.data.remote.HomeRequestService
import com.example.wordsapp.home.data.repository.RoomsRepositoryImpl
import com.example.wordsapp.home.domain.repository.RoomsRepository
import com.google.firebase.firestore.FirebaseFirestore
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
    @Provides
    @Singleton
    fun  provideRoomRepository(firestore: FirebaseFirestore, homeRequestService: HomeRequestService,socketHandler: SocketHandler): RoomsRepository {
        return RoomsRepositoryImpl(firestore, homeRequestService,socketHandler )
    }
}


