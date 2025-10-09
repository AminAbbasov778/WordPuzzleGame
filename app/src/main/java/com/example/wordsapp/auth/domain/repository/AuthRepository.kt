package com.example.wordsapp.auth.domain.repository

interface AuthRepository {
    suspend fun signIn(email: String, password: String): Result<Unit>
    suspend fun signUp(email: String, password: String): Result<Unit>
    fun signOut()
    suspend fun signInAnonymously(): Result<Unit>
    fun getUserUid() : String?

}