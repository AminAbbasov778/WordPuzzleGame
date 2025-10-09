package com.example.wordsapp.core.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.wordsapp.core.data.repositories.UserRepositoryImpl
import com.example.wordsapp.core.domain.repositories.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(
        auth: FirebaseAuth,
        dataStore: DataStore<Preferences>,
        fireStore: FirebaseFirestore
    ): UserRepository {
        return UserRepositoryImpl(auth, dataStore, fireStore)

    }
}