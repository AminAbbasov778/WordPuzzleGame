package com.example.wordsapp.game.data.repository

import com.example.wordsapp.core.network.SocketHandler
import com.example.wordsapp.game.data.mapper.toData
import com.example.wordsapp.game.data.mapper.toDomain
import com.example.wordsapp.game.domain.model.GameOverModel
import com.example.wordsapp.game.domain.model.GameSettingsUpdateModel
import com.example.wordsapp.game.domain.model.GameStartedModel
import com.example.wordsapp.game.domain.model.GameUpdateModel
import com.example.wordsapp.game.domain.model.GuessLetterModel
import com.example.wordsapp.game.domain.model.GuessWordModel
import com.example.wordsapp.game.domain.model.LeaveRoomModel
import com.example.wordsapp.game.domain.model.PlayerEliminatedModel
import com.example.wordsapp.game.domain.model.PlayerLeftModel
import com.example.wordsapp.game.domain.model.PlayerReadyUpdatedModel
import com.example.wordsapp.game.domain.model.ReadyModel
import com.example.wordsapp.game.domain.model.TurnModel
import com.example.wordsapp.game.domain.model.UnreadyUpdateModel
import com.example.wordsapp.game.domain.repository.GameRepository
import com.example.wordsapp.home.data.mapper.toDomain
import com.example.wordsapp.home.domain.model.PlayerJoinedModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(
    private val socketHandler: SocketHandler
) : GameRepository {

    override fun playerJoinedFlow(): Flow<PlayerJoinedModel> {
        return socketHandler.playerJoinedFlow.map { it.toDomain() }
    }

    override fun playerReadyUpdatedFlow(): Flow<PlayerReadyUpdatedModel> {
        return socketHandler.playerReadyUpdatedFlow.map { it.toDomain() }
    }

    override fun playerLeftFlow(): Flow<PlayerLeftModel> {
        return socketHandler.playerLeftFlow.map { it.toDomain() }
    }

    override fun gameStartedFlow(): Flow<GameStartedModel> {
        return socketHandler.gameStartedFlow.map { it.toDomain() }
    }

    override fun turnFlow(): Flow<TurnModel> {
        return socketHandler.turnFlow.map { it.toDomain() }
    }

    override fun gameUpdateFlow(): Flow<GameUpdateModel> {
        return socketHandler.gameUpdateFlow.map { it.toDomain() }
    }

    override fun gameSettingsFlow(): Flow<GameSettingsUpdateModel> {
        return socketHandler.gameSettingsFlow.map { it.toDomain() }
    }

    override fun playerEliminatedFlow(): Flow<PlayerEliminatedModel> {
        return socketHandler.playerEliminatedFlow.map { it.toDomain() }
    }

    override fun gameOverFlow(): Flow<GameOverModel> {
        return socketHandler.gameOverFlow.map { it.toDomain() }
    }

    override fun ready(ready: ReadyModel) {
        socketHandler.ready(ready.toData())
    }

    override fun unreadyUpdate(unready: UnreadyUpdateModel) {
        socketHandler.unreadyUpdate(unready.toData())
    }

    override fun leaveRoom(leaveRoom: LeaveRoomModel) {
        socketHandler.leaveRoom(leaveRoom.toData())
    }

    override fun guessLetter(guessLetter: GuessLetterModel) {
        socketHandler.guessLetter(guessLetter.toData())
    }

    override fun guessWord(guessWord: GuessWordModel) {
        socketHandler.guessWord(guessWord.toData())
    }
}
