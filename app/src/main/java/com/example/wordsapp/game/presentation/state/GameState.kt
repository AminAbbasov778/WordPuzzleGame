package com.example.wordsapp.game.presentation.state

import com.example.wordsapp.core.presentation.base.UIState
import com.example.wordsapp.game.presentation.model.GameOverUi
import com.example.wordsapp.game.presentation.model.GameSettingsUpdateUi
import com.example.wordsapp.game.presentation.model.GameStartedUi
import com.example.wordsapp.game.presentation.model.GameUpdateUi
import com.example.wordsapp.game.presentation.model.LetterUi
import com.example.wordsapp.game.presentation.model.PlayerEliminatedUi
import com.example.wordsapp.game.presentation.model.PlayerLeftUi
import com.example.wordsapp.game.presentation.model.PlayerReadyUpdatedUi
import com.example.wordsapp.game.presentation.model.ReadyPlayerUi
import com.example.wordsapp.game.presentation.model.TurnUi
import com.example.wordsapp.home.domain.model.RoomUpdateModel
import com.example.wordsapp.home.presentation.model.GameRouteUi
import com.example.wordsapp.game.presentation.model.PlayerJoinedUi

data class GameState(
    val isLoading: Boolean = false, val isReady: Boolean = false,
    val isGameStarted: Boolean = false, val isCancelled: Boolean = true,
    val customWord: String = "",
    val currentPlayerCount: Int = 0,
    val playerJoined: PlayerJoinedUi? = null,
    val playerReadyUpdated: PlayerReadyUpdatedUi? = null,
    val joinedList : List<PlayerJoinedUi> = emptyList(),
    val readyList : List<ReadyPlayerUi> = emptyList(),
    val playerLeft: PlayerLeftUi? = null,
    val turnTimer: Int = 30,
    val gameStarted: GameStartedUi? = null,
    val readyPlayerCount: Int = 0,
    val gameRouteUi: GameRouteUi = GameRouteUi(),
    val turn: TurnUi? = null,
    val isYourTurn: Boolean = false,
    val whichPlayerTurn : Int = 0,
    var isLeft: Boolean = false,
    val gameUpdateUi: GameUpdateUi? = null,
    val gameSettings: GameSettingsUpdateUi? = null,
    val playerEliminated: PlayerEliminatedUi? = null,
    val gameOver: GameOverUi? = null,
    val isGameOver: Boolean = false,
    val roomUpdate: RoomUpdateModel? = null,
    val letters: List<LetterUi> = emptyList(),
    val isReadyPlayerSheetOpen: Boolean = false,
    val totalWrongGuesses: Int = 0,
    var isObservingFlows : Boolean = false,
    val isCustomWordVisible : Boolean = false,
    val guessingCounts : Int = 0,
    val isBack : Boolean = false,
    val isGuessesVisible : Boolean = false,
    val isWordVisible : Boolean = false,
) : UIState{
}