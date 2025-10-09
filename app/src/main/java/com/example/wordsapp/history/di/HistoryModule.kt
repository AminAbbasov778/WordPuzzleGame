package com.example.wordsapp.history.di

import com.example.wordsapp.history.data.remote.HistoryRequestService
import com.example.wordsapp.history.data.repository.HistoryRepositoryImpl
import com.example.wordsapp.history.domain.repository.HistoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent :: class)
object HistoryModule {
    @Provides
    @Singleton
    fun  provideHistoryRequestService(retrofit: Retrofit): HistoryRequestService {
        return retrofit.create(HistoryRequestService :: class.java)
    }

    @Provides
    @Singleton
    fun provideHistoryRepository(historyRequestService: HistoryRequestService): HistoryRepository {
        return HistoryRepositoryImpl(historyRequestService)


}
}