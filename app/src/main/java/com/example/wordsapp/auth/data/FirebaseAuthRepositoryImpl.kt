package com.example.wordsapp.auth.data

import android.util.Log
import com.example.wordsapp.auth.domain.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
): AuthRepository {
    override suspend fun signIn(email: String, password: String): Result<Unit> {
        return try {
            val user = auth.signInWithEmailAndPassword(email, password).await().user!!
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signUp(email: String, password: String): Result<Unit> {
        return try {
            val user = auth.createUserWithEmailAndPassword(email, password).await().user!!

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    override fun getUserUid() : String? {
        return auth.currentUser?.uid

    }


    override suspend fun signInAnonymously(): Result<Unit> {
        return try {
            val user = auth.signInAnonymously().await().user!!
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

   override fun signOut(){
        auth.signOut()
    }


}