package com.example.wordsapp.game

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wordsapp.core.data.models.GameOver
import com.example.wordsapp.core.domain.ConnectSocketUseCase
import com.example.wordsapp.core.domain.DisconnectUseCase
import com.example.wordsapp.game.domain.GameOverFlowUseCase
import com.example.wordsapp.game.domain.GameSettingsFlowUseCase
import com.example.wordsapp.game.domain.GameStartedFlowUseCase
import com.example.wordsapp.game.domain.GameUpdateFlowUseCase
import com.example.wordsapp.game.domain.GetRoomStateUseCase
import com.example.wordsapp.game.domain.GuessLetterUseCase
import com.example.wordsapp.game.domain.GuessWordUseCase
import com.example.wordsapp.game.domain.LeaveRoomUseCase
import com.example.wordsapp.game.domain.PlayerEliminatedFlowUseCase
import com.example.wordsapp.game.domain.PlayerJoinedFlowUseCase
import com.example.wordsapp.game.domain.PlayerLeftFlowUseCase
import com.example.wordsapp.game.domain.PlayerReadyUpdatedFlowUseCase
import com.example.wordsapp.game.domain.ReadyUseCase
import com.example.wordsapp.game.domain.TurnFlowUseCase
import com.example.wordsapp.game.domain.UnreadyUpdateUseCase
import com.example.wordsapp.home.presentation.PlayerJoinedUi
import com.example.wordsapp.home.presentation.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import io.socket.client.Socket
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val playerJoinedFlowUseCase: PlayerJoinedFlowUseCase,
    private val playerReadyUpdatedFlowUseCase: PlayerReadyUpdatedFlowUseCase,
    private val playerLeftFlowUseCase: PlayerLeftFlowUseCase,
    private val gameStartedFlowUseCase: GameStartedFlowUseCase,
    private val turnFlowUseCase: TurnFlowUseCase,
    private val gameUpdateFlowUseCase: GameUpdateFlowUseCase,
    private val gameSettingsFlowUseCase: GameSettingsFlowUseCase,
    private val playerEliminatedFlowUseCase: PlayerEliminatedFlowUseCase,
    private val gameOverFlowUseCase: GameOverFlowUseCase,
    private val leaveRoomUseCase: LeaveRoomUseCase,
    private val readyUseCase: ReadyUseCase,
    private val unreadyUpdateUseCase: UnreadyUpdateUseCase,
    private val guessLetterUseCase: GuessLetterUseCase,
    private val guessWordUseCase: GuessWordUseCase,
    private val disconnectUseCase: DisconnectUseCase,
    private val connectUseCase: ConnectSocketUseCase,

    private val getRoomStateUseCase: GetRoomStateUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(GameState())
    val state: StateFlow<GameState> = _state.asStateFlow()
    private var timerJob: Job? = null
    private var guessJob: Job? = null

    init {


        _state.value = GameState(
            letters = getInitialLetters(),
            isObservingFlows = false,

        )
        observeFlows()
        onEvent(GameIntent.GetLetters)

    }



    private fun getInitialLetters(): List<LetterUi> {
        val alphabet = if (_state.value.gameRouteUi.language == "az") {
            listOf("A", "B", "C", "Ç", "D", "E", "Ə", "F", "G", "Ğ", "H", "X", "I", "İ", "J", "K", "Q", "L", "M", "N", "O", "Ö", "P", "R", "S", "Ş", "T", "U", "Ü", "V", "Y", "Z")
        } else {
            listOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z")
        }
        return alphabet.map { LetterUi(it) }
    }
    override fun onCleared() {

        timerJob?.cancel()
        super.onCleared()
    }


    private fun observeFlows() {
        if(_state.value.isObservingFlows) return
        _state.update { it.copy(isObservingFlows = true) }
        Log.e("Socket", "observeFlows: Called COunt  ")
        playerJoinedFlowUseCase().onEach {
            Log.e("Socket", "observeFlows:  do i joined ${it.toString()}  ")

            if(it.userId != state.value.gameRouteUi.userUid){
                _state.update { s ->
                    s.copy(
                        playerJoined = it.toUi(),
                        joinedList = s.joinedList + it.toUi(),
                        currentPlayerCount = s.currentPlayerCount + 1
                    )
                }
            }

        }.launchIn(viewModelScope)

        playerReadyUpdatedFlowUseCase().onEach { update ->
            _state.update { s ->
                val newReadyList = s.joinedList.map { player ->
                    if (player.userId == update.userId) player.copy(isReady = update.ready) else player
                }

                s.copy(
                    playerReadyUpdated = update,
                    joinedList = newReadyList
                )
            }
        }.launchIn(viewModelScope)

        playerLeftFlowUseCase().onEach {
            _state.update { s ->
                s.copy(
                    playerLeft = it,
                    joinedList = s.joinedList.filter { player -> player.userId != it.userId }
                    ,
                    currentPlayerCount = s.currentPlayerCount - 1
                )
            }
        }.launchIn(viewModelScope)

        gameStartedFlowUseCase().onEach {
            _state.update { s -> s.copy(gameStarted = it, isGameStarted = true,gameUpdateUi = GameUpdateUi(discovered = List(it.wordLength) { "_" }, guessedBy = "", guessedLetter = "", correct = false, playerScore = 0)) }
        }.launchIn(viewModelScope)

        turnFlowUseCase().onEach {

            _state.update { s ->
                s.copy(
                    turn = it,
                    isYourTurn = s.gameRouteUi.userUid == it.userId
                )
            }
            if (it.userId.isNotEmpty()) {
                startTurnTimer()
            }

        }.launchIn(viewModelScope)

        gameUpdateFlowUseCase().onEach { gameUpdate ->
            Log.e("SocketView", "observeFlows: ${gameUpdate.toString()}")
            val updatedLetters = _state.value.letters.map { letter ->


                if (gameUpdate.guessedLetter.lowercase() == letter.char.lowercase()) {
                    Log.e("SocketView", "isEntered: ${gameUpdate.toString()}")

                    letter.copy(
                        isSelected = true,
                        isCorrect = gameUpdate.correct
                    )
                } else {
                    letter
                }
            }

                _state.update { s -> s.copy(guessingCounts = s.guessingCounts + 1) }


            if (!gameUpdate.correct) {
                _state.update { s -> s.copy(totalWrongGuesses = s.totalWrongGuesses + 1) }
            }


            _state.update { s -> s.copy(gameUpdateUi = s.gameUpdateUi?.copy(discovered = gameUpdate.discovered ?: s.gameUpdateUi.discovered , guessedBy = gameUpdate.guessedBy, guessedLetter = gameUpdate.guessedLetter, correct = gameUpdate.correct, playerScore = gameUpdate.playerScore), letters = updatedLetters) }

        }.launchIn(viewModelScope)


        gameSettingsFlowUseCase().onEach {
            _state.update { s -> s.copy(gameSettings = it) }
        }.launchIn(viewModelScope)

        playerEliminatedFlowUseCase().onEach {
            _state.update { s -> s.copy(playerEliminated = it, isGameOver =if(it.userId == state.value.gameRouteUi.userUid) true else false, joinedList = s.joinedList.filter { player -> player.userId != it.userId }) }
        }.launchIn(viewModelScope)

        gameOverFlowUseCase().onEach {
            _state.update { s -> s.copy(gameOver = it) }
        }.launchIn(viewModelScope)

        getHomeState()
    }



    fun onEvent(intent: GameIntent) {

        when (intent) {
            is GameIntent.LeaveRoom -> {
                _state.update {
                    it.copy(
                        currentPlayerCount = it.currentPlayerCount - 1,
                        joinedList = it.joinedList.filter { player -> player.userId != intent.leaveRoom.userId }
                        ,
                        isLeft = true,

                    )
                }

                leaveRoomUseCase(intent.leaveRoom)
            }

            is GameIntent.Ready -> {
                readyUseCase(intent.ready)
                _state.update { it.copy(isReady = true, isCancelled = false) }
            }

            is GameIntent.UnreadyUpdate -> {
                unreadyUpdateUseCase(intent.unready)
                _state.update { it.copy(isReady = false, isCancelled = true) }
            }

            is GameIntent.GuessLetter -> {
                guessLetterUseCase(intent.guessLetter)
                val updatedLetters = _state.value.letters.map { letter ->

                    if (intent.guessLetter.letter == letter.char) {
                        letter.copy(
                            char = intent.guessLetter.letter,
                            isSelected = true,
                        )
                    } else {
                        letter
                    }
                }
                _state.update { s -> s.copy(letters = updatedLetters) }
            }
            is GameIntent.GuessWord -> guessWordUseCase(intent.guessWord)
            is GameIntent.InputText -> _state.update { it.copy(customWord = intent.text) }
            is GameIntent.UpdateGame -> {
                _state.update {
                    it.copy(
                        gameRouteUi = it.gameRouteUi.copy(
                            roomId = intent.update.roomId,
                            roomName = intent.update.roomName,
                            maxPlayers = intent.update.maxPlayers,
                            difficulty = intent.update.difficulty,
                            language = intent.update.language,
                            userUid = intent.update.userUid,
                            status = intent.update.status, createdAt = intent.update.createdAt,
                            username = intent.update.username
                        ),

                    )

                }


            }

            GameIntent.ClearState -> clearState()
            is GameIntent.LetterClicked -> TODO()
            GameIntent.GetLetters -> getLetters()
            GameIntent.ReadyPlayerSheetVisibility -> _state.update { it.copy(isReadyPlayerSheetOpen = !it.isReadyPlayerSheetOpen) }
            GameIntent.CustomWordVisibility -> _state.update { it.copy(isCustomWordVisible = !it.isCustomWordVisible) }
            GameIntent.GoBack -> _state.update { it.copy(isBack = !it.isBack) }
            GameIntent.ChangeWordVisibility -> changeWordVisibility()

    }}

    fun changeWordVisibility(){
        _state.update { it.copy(isWordVisible = !it.isWordVisible) }
    }


    private fun clearState() {


        timerJob?.cancel()
    }

    private fun startTurnTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            (30 downTo 0).forEach { second ->
                _state.update { it.copy(turnTimer = second) }
                delay(1000L)
            }

        }

    }

    fun getLetters() {
        val az = listOf(
            "Q", "Ü", "E", "R", "T", "U", "İ", "O", "P", "A",
            "S", "D", "F", "G", "H", "J", "K", "L", "Z", "X",
            "C", "V", "B", "N", "M", "Ö", "Ğ", "I", "Ə", "Ç",
            "Ş"
        )

        val en = listOf(
            "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P",
            "A", "S", "D", "F", "G", "H", "J", "K", "L",
            "Z", "X", "C", "V", "B", "N", "M"
        )


        val tr = listOf(
            "F", "G", "Ğ", "O", "D", "R", "N", "H", "P", "Q",
            "U", "Ü", "İ", "E", "A", "K", "M", "L", "Y", "Ş",
            "C", "T", "S", "Z", "V", "B", "Ö", "Ç", "X", "J"
        )

        val alphabet = if (state.value.gameRouteUi.language == "az") az else if (state.value.gameRouteUi.language == "tr") tr else if (state.value.gameRouteUi.language == "en") en else tr

        _state.update { it.copy(letters = alphabet.map { LetterUi(it) }) }
    }


    fun isGuessesVisible(){
        guessJob?.cancel()

       guessJob =  viewModelScope.launch {
            _state.update { it.copy(isGuessesVisible = true) }
            delay(2000L)
            _state.update { it.copy(isGuessesVisible = false) }
        }

    }

    fun getHomeState() {
        viewModelScope.launch {
            getRoomStateUseCase().collect {  homeState ->
                Log.e("Socket", "getHomeState: ${homeState.toString()}", )
                if(homeState.roomId == state.value.gameRouteUi.roomId){
                    _state.update {gameState -> gameState.copy(currentPlayerCount = gameState.currentPlayerCount + 1,joinedList =  gameState.joinedList + homeState.players.map { PlayerJoinedUi( it.id, it.name, it.ready) }) }

                }
            }

        }
    }


}
