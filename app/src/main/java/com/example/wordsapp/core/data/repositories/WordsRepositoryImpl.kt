package com.example.wordsapp.core.data.repositories

import com.example.wordsapp.core.data.models.*
import com.example.wordsapp.core.domain.repositories.WordsRepository
import com.example.wordsapp.core.network.SocketHandler
import com.example.wordsapp.home.data.toData
import com.example.wordsapp.home.data.toDomain
import com.example.wordsapp.home.domain.JoinRoomModel
import com.example.wordsapp.home.domain.PlayerJoinedModel
import com.example.wordsapp.home.domain.RoomUpdateModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WordsRepositoryImpl @Inject constructor(
    private val socketHandler: SocketHandler
) : WordsRepository {

    override fun connectSocket()  {
        socketHandler.connect()
    }

    override fun isConnected(): Boolean {
        return socketHandler.isConnected()
    }
    override fun disconnectSocket()  {
        socketHandler.disconnect()
    }
    override fun emitToken(token: String) {
        socketHandler.emitToken(token)
    }

    override fun playerJoinedFlow(): Flow<PlayerJoinedModel> {
        return socketHandler.playerJoinedFlow.map { it.toDomain() }
    }

    override fun playerReadyUpdatedFlow(): Flow<PlayerReadyUpdated> {
        return socketHandler.playerReadyUpdatedFlow
    }

    override fun playerLeftFlow(): Flow<PlayerLeft> {
        return socketHandler.playerLeftFlow
    }

    override fun gameStartedFlow(): Flow<GameStarted> {
        return socketHandler.gameStartedFlow
    }

    override fun turnFlow(): Flow<Turn> {
        return socketHandler.turnFlow
    }

    override fun gameUpdateFlow(): Flow<GameUpdate> {
        return socketHandler.gameUpdateFlow
    }

    override fun gameSettingsFlow(): Flow<GameSettingsUpdate> {
        return socketHandler.gameSettingsFlow
    }

    override fun playerEliminatedFlow(): Flow<PlayerEliminated> {
        return socketHandler.playerEliminatedFlow
    }

    override fun gameOverFlow(): Flow<GameOver> {
        return socketHandler.gameOverFlow
    }

    override fun roomUpdateFlow(): Flow<RoomUpdateModel> {
        return socketHandler.roomUpdateFlow.map { it.toDomain() }
    }

    override fun roomStateFlow(): Flow<RoomState> {
        return socketHandler.roomStateFlow
    }

    override fun joinRoom(joinRoom: JoinRoomModel) = socketHandler.joinRoom(joinRoom.toData())
    override fun ready(ready: Ready) = socketHandler.ready(ready)
    override fun unreadyUpdate(unready: UnreadyUpdate) = socketHandler.unreadyUpdate(unready)
    override fun leaveRoom(leaveRoom: LeaveRoom) = socketHandler.leaveRoom(leaveRoom)
    override fun guessLetter(guessLetter: GuessLetter) = socketHandler.guessLetter(guessLetter)
    override fun guessWord(guessWord: GuessWord) = socketHandler.guessWord(guessWord)
}
