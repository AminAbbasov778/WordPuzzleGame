package com.example.wordsapp.core.domain.repositories

import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun saveUsername(username: String): Result<Unit>
    fun getUsernameFromLocal(): Flow<String?>
    suspend fun clearUsername(): Result<Unit>
    suspend fun saveUsernameToRemote(username: String): Result<Unit>
    suspend fun getUsernameFromRemote(): Result<String>

}