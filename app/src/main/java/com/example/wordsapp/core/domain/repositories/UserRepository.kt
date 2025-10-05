package com.example.wordsapp.core.domain.repositories

import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun saveUsername(username: String)
    fun getUsernameFromLocal(): Flow<String?>
    suspend fun getUsernameFromRemote(): String?
    fun saveUsernameToRemote(username: String)
    suspend  fun clearUsername()

}