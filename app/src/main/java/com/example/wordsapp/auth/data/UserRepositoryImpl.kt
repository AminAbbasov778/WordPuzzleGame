package com.example.wordsapp.auth.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.wordsapp.auth.utils.Constants.USERNAME
import com.example.wordsapp.core.domain.repositories.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val auth: FirebaseAuth, private val dataStore: DataStore<Preferences>, private val fireStore: FirebaseFirestore) :
    UserRepository {

    override suspend fun saveUsername(username: String) {
        dataStore.edit { prefs ->
            prefs[USERNAME] = username
        }
    }

    override  fun getUsernameFromLocal(): Flow<String?> =
        dataStore.data.map { prefs ->
            prefs[USERNAME]
        }

   override suspend  fun clearUsername() {
        dataStore.edit { prefs ->
            prefs.remove(USERNAME)
        }
    }



   override fun saveUsernameToRemote(username: String) {
        fireStore.collection("users").document(auth.currentUser!!.uid).set(mapOf("username" to username))
    }
   override suspend fun getUsernameFromRemote(): String? {
        val snapshot = fireStore.collection("users")
            .document(auth.currentUser!!.uid)
            .get()
            .await()
        return snapshot.getString("username")
    }



}