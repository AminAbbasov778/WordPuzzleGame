package com.example.wordsapp.auth.domain

import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    suspend fun signIn(email: String, password: String): Result<Unit>
    suspend fun signUp(email: String, password: String): Result<Unit>
    fun signOut()
    suspend fun signInAnonymously(): Result<Unit>
    fun getUserUid() : String?

}