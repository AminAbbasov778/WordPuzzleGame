package com.example.wordsapp.home.data

import com.example.wordsapp.core.domain.repositories.RoomsRepository
import com.example.wordsapp.home.data.remote.HomeRequestService
import com.example.wordsapp.home.domain.RoomModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RoomsRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,private val homeRequestService: HomeRequestService
) : RoomsRepository {

    private var listener: ListenerRegistration? = null

    override fun getWaitingRooms(): Flow<List<RoomModel>> = callbackFlow {
        val query = firestore.collection("rooms")

        listener = query.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }
            val rooms = snapshot?.toObjects(Room::class.java) ?: emptyList()
            trySend(rooms.map { it.toDomain() })
        }

        awaitClose { listener?.remove() }
    }

    override fun getRooms(status: Int): Flow<Result<com.example.wordsapp.home.data.module.room.Room>> = flow {
        try {
            val response = homeRequestService.getRooms(status)
            emit(Result.success(response))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }


}