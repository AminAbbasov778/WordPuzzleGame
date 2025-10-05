package com.example.wordsapp.game

import com.example.wordsapp.core.data.models.GameOver
import com.example.wordsapp.core.data.models.GameSettingsUpdate
import com.example.wordsapp.core.data.models.GameStarted
import com.example.wordsapp.core.data.models.GameUpdate
import com.example.wordsapp.core.data.models.PlayerEliminated
import com.example.wordsapp.core.data.models.PlayerLeft
import com.example.wordsapp.core.data.models.PlayerReadyUpdated
import com.example.wordsapp.core.data.models.Turn
import com.example.wordsapp.home.domain.RoomUpdateModel
import com.example.wordsapp.home.presentation.GameRouteUi
import com.example.wordsapp.home.presentation.PlayerJoinedUi

data class GameState(
    val isLoading: Boolean = false, val isReady: Boolean = false,
    val isGameStarted: Boolean = false, val isCancelled: Boolean = true,
    val customWord: String = "",
    val currentPlayerCount: Int = 0,
    val playerJoined: PlayerJoinedUi? = null,
    val playerReadyUpdated: PlayerReadyUpdated? = null,
    val joinedList : List<PlayerJoinedUi> = emptyList(),
    val readyList : List<ReadyPlayerUi> = emptyList(),
    val playerLeft: PlayerLeft? = null,
    val turnTimer: Int = 30,
    val gameStarted: GameStarted? = null,
    val readyPlayerCount: Int = 0,
    val gameRouteUi: GameRouteUi = GameRouteUi(),
    val turn: Turn? = null,
    val isYourTurn: Boolean = false,
    val whichPlayerTurn : Int = 0,
    var isLeft: Boolean = false,
    val gameUpdateUi: GameUpdateUi? = null,
    val gameSettings: GameSettingsUpdate? = null,
    val playerEliminated: PlayerEliminated? = null,
    val gameOver: GameOver? = null,
    val isGameOver: Boolean = false,
    val roomUpdate: RoomUpdateModel? = null,
    val letters: List<LetterUi> = emptyList(),
    val isReadyPlayerSheetOpen: Boolean = false,
    val totalWrongGuesses: Int = 0,
     var isObservingFlows : Boolean = false,
    val isCustomWordVisible : Boolean = false,
    val guessingCounts : Int = 0,
    val isBack : Boolean = false,
    val isGuessesVisible : Boolean = false
) {
}