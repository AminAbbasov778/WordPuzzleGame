package com.example.wordsapp.core.di

import androidx.datastore.core.DataStore
import com.example.wordsapp.auth.data.FirebaseAuthRepositoryImpl
import com.example.wordsapp.auth.domain.AuthRepository
import com.example.wordsapp.auth.data.UserRepositoryImpl
import com.example.wordsapp.core.network.SocketHandler
import com.example.wordsapp.home.data.RoomsRepositoryImpl
import com.example.wordsapp.core.data.repositories.WordsRepositoryImpl
import com.example.wordsapp.core.domain.repositories.RoomsRepository
import com.example.wordsapp.core.domain.repositories.UserRepository
import com.example.wordsapp.core.domain.repositories.WordsRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import androidx.datastore.preferences.core.Preferences
import com.example.wordsapp.history.data.remote.HistoryRequestService
import com.example.wordsapp.history.data.repository.HistoryRepositoryImpl
import com.example.wordsapp.history.domain.repository.HistoryRepository
import com.example.wordsapp.home.data.remote.HomeRequestService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent :: class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideWordsRepository(socketHandler: SocketHandler): WordsRepository {
        return WordsRepositoryImpl(socketHandler)

    }

    @Provides
    @Singleton
    fun  provideRoomRepository(firestore: FirebaseFirestore,homeRequestService: HomeRequestService): RoomsRepository {
        return RoomsRepositoryImpl(firestore,homeRequestService)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(firebaseAuth: FirebaseAuth): AuthRepository {
        return FirebaseAuthRepositoryImpl(firebaseAuth)

    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(auth: FirebaseAuth,dataStore : DataStore<Preferences>,firebaseFirestore: FirebaseFirestore): UserRepository{
        return UserRepositoryImpl(auth,dataStore,firebaseFirestore)
    }

    @Provides
    @Singleton
    fun provideHistoryRepository(historyRequestService: HistoryRequestService): HistoryRepository {
        return HistoryRepositoryImpl(historyRequestService)

}}