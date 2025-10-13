package com.example.wordsapp.home.data.repository

import android.util.Log
import com.example.wordsapp.core.network.SocketHandler
import com.example.wordsapp.game.data.mapper.toDomain
import com.example.wordsapp.game.domain.model.HomeStateModel
import com.example.wordsapp.home.data.mapper.toData
import com.example.wordsapp.home.data.mapper.toDomain
import com.example.wordsapp.home.data.remote.HomeRequestService
import com.example.wordsapp.home.domain.model.JoinRoomModel
import com.example.wordsapp.home.domain.model.RoomUpdateModel
import com.example.wordsapp.home.domain.repository.RoomsRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomsRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore, private val homeRequestService: HomeRequestService,val socketHandler: SocketHandler
) : RoomsRepository {


    override fun getRooms(status: Int): Flow<Result<com.example.wordsapp.home.data.model.room.Room>> =
        flow {

                try {
                    val response = homeRequestService.getRooms(status)

                    if (response.data?.rooms != null) {
                        emit(Result.success(response))
                    } else {
                        Log.e("roomsrepo", "getRooms:empty", )

                        emit(Result.failure(Exception("Empty room list or null data")))
                    }

                } catch (e: Exception) {
                    Log.e("roomsrepo", "getRooms: ${e.localizedMessage}", )
                    emit(Result.failure(e))
                }

        }


    override fun connectSocket()  {
        socketHandler.connect()
    }

    override fun isConnected(): Boolean {
        return socketHandler.isConnected()
    }
    override fun disconnectSocket()  {
        socketHandler.disconnect()
    }



    override fun roomUpdateFlow(): Flow<RoomUpdateModel> {
        return socketHandler.roomUpdateFlow.map { it.toDomain() }
    }

    override fun roomStateFlow(): Flow<HomeStateModel> {
        return socketHandler.homeStateFlow.map { it.toDomain() }
    }

    override fun joinRoom(joinRoom: JoinRoomModel) = socketHandler.joinRoom(joinRoom.toData())


}