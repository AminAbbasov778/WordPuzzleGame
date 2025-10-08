package com.example.wordsapp.core.domain.repositories

import com.example.wordsapp.core.data.models.GameOver
import com.example.wordsapp.core.data.models.GameSettingsUpdate
import com.example.wordsapp.core.data.models.GameStarted
import com.example.wordsapp.core.data.models.GameUpdate
import com.example.wordsapp.core.data.models.GuessLetter
import com.example.wordsapp.core.data.models.GuessWord
import com.example.wordsapp.core.data.models.JoinRoom
import com.example.wordsapp.core.data.models.LeaveRoom
import com.example.wordsapp.core.data.models.PlayerEliminated
import com.example.wordsapp.core.data.models.PlayerJoined
import com.example.wordsapp.core.data.models.PlayerLeft
import com.example.wordsapp.core.data.models.PlayerReadyUpdated
import com.example.wordsapp.core.data.models.Ready
import com.example.wordsapp.core.data.models.RoomState
import com.example.wordsapp.core.data.models.Turn
import com.example.wordsapp.core.data.models.UnreadyUpdate
import com.example.wordsapp.home.domain.JoinRoomModel
import com.example.wordsapp.home.domain.PlayerJoinedModel
import com.example.wordsapp.home.domain.RoomUpdateModel
import kotlinx.coroutines.flow.Flow

interface WordsRepository {
    fun connectSocket()
    fun disconnectSocket()
    fun emitToken(token: String)
    fun playerJoinedFlow(): Flow<PlayerJoinedModel>
    fun playerReadyUpdatedFlow(): Flow<PlayerReadyUpdated>
    fun playerLeftFlow(): Flow<PlayerLeft>
    fun gameStartedFlow(): Flow<GameStarted>
    fun turnFlow(): Flow<Turn>
    fun gameUpdateFlow(): Flow<GameUpdate>
    fun gameSettingsFlow(): Flow<GameSettingsUpdate>
    fun playerEliminatedFlow(): Flow<PlayerEliminated>
    fun gameOverFlow(): Flow<GameOver>
    fun roomUpdateFlow(): Flow<RoomUpdateModel>
    fun roomStateFlow(): Flow<RoomState>

    fun joinRoom(joinRoom: JoinRoomModel)
    fun ready(ready: Ready)
    fun unreadyUpdate(unready: UnreadyUpdate)
    fun leaveRoom(leaveRoom: LeaveRoom)
    fun guessLetter(guessLetter: GuessLetter)
    fun guessWord(guessWord: GuessWord)
    fun isConnected(): Boolean
}