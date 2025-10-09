package com.example.wordsapp.game.domain.repository

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
import com.example.wordsapp.home.domain.model.PlayerJoinedModel
import kotlinx.coroutines.flow.Flow

interface GameRepository {
    fun playerJoinedFlow(): Flow<PlayerJoinedModel>
    fun playerReadyUpdatedFlow(): Flow<PlayerReadyUpdatedModel>
    fun playerLeftFlow(): Flow<PlayerLeftModel>
    fun gameStartedFlow(): Flow<GameStartedModel>
    fun turnFlow(): Flow<TurnModel>
    fun gameUpdateFlow(): Flow<GameUpdateModel>
    fun gameSettingsFlow(): Flow<GameSettingsUpdateModel>
    fun playerEliminatedFlow(): Flow<PlayerEliminatedModel>
    fun gameOverFlow(): Flow<GameOverModel>
    fun ready(ready: ReadyModel)
    fun unreadyUpdate(unready: UnreadyUpdateModel)
    fun leaveRoom(leaveRoom: LeaveRoomModel)
    fun guessLetter(guessLetter: GuessLetterModel)
    fun guessWord(guessWord: GuessWordModel)

}