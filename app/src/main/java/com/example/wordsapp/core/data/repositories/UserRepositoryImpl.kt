package com.example.wordsapp.core.data.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.wordsapp.auth.utils.Constants
import com.example.wordsapp.core.domain.repositories.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val dataStore: DataStore<Preferences>,
    private val fireStore: FirebaseFirestore
) : UserRepository {

    override suspend fun saveUsername(username: String): Result<Unit> {
        return try {
            dataStore.edit { prefs ->
                prefs[Constants.USERNAME] = username
            }
            Result.success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override fun getUsernameFromLocal(): Flow<String?> =
        dataStore.data.map { prefs -> prefs[Constants.USERNAME] }

    override suspend fun clearUsername(): Result<Unit> {
        return try {
            dataStore.edit { prefs -> prefs.remove(Constants.USERNAME) }
            Result.success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun saveUsernameToRemote(username: String): Result<Unit> {
        return try {
            val currentUser = auth.currentUser
            if (currentUser != null) {
                fireStore.collection("users")
                    .document(currentUser.uid)
                    .set(mapOf("username" to username))
                Result.success(Unit)
            } else {
                Result.failure(Exception("Current user is null"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun getUsernameFromRemote(): Result<String> {
        val currentUser = auth.currentUser
        if (currentUser == null) return Result.failure(Exception("Current user is null"))

        return try {
            val snapshot = fireStore.collection("users")
                .document(currentUser.uid)
                .get()
                .await()
            val username = snapshot.getString("username") ?: "Guest"
            Result.success(username)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}