package com.example.wordsapp.game.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wordsapp.core.presentation.base.BaseViewModel
import com.example.wordsapp.game.domain.usecase.GameOverFlowUseCase
import com.example.wordsapp.game.domain.usecase.GameSettingsFlowUseCase
import com.example.wordsapp.game.domain.usecase.GameStartedFlowUseCase
import com.example.wordsapp.game.domain.usecase.GameUpdateFlowUseCase
import com.example.wordsapp.game.domain.usecase.GetHomeStateUseCase
import com.example.wordsapp.game.domain.usecase.GuessLetterUseCase
import com.example.wordsapp.game.domain.usecase.GuessWordUseCase
import com.example.wordsapp.game.domain.usecase.LeaveRoomUseCase
import com.example.wordsapp.game.domain.usecase.PlayerEliminatedFlowUseCase
import com.example.wordsapp.game.domain.usecase.PlayerJoinedFlowUseCase
import com.example.wordsapp.game.domain.usecase.PlayerLeftFlowUseCase
import com.example.wordsapp.game.domain.usecase.PlayerReadyUpdatedFlowUseCase
import com.example.wordsapp.game.domain.usecase.ReadyUseCase
import com.example.wordsapp.game.domain.usecase.TurnFlowUseCase
import com.example.wordsapp.game.domain.usecase.UnreadyUpdateUseCase
import com.example.wordsapp.game.presentation.intent.GameIntent
import com.example.wordsapp.game.presentation.mapper.toDomain
import com.example.wordsapp.game.presentation.mapper.toUI
import com.example.wordsapp.game.presentation.mapper.toUi
import com.example.wordsapp.game.presentation.model.GameUpdateUi
import com.example.wordsapp.game.presentation.model.LetterUi
import com.example.wordsapp.game.presentation.state.GameState
import com.example.wordsapp.home.presentation.mapper.toUi
import com.example.wordsapp.game.presentation.model.PlayerJoinedUi
import com.example.wordsapp.game.presentation.navigation.GameNavigation
import dagger.hilt.android.lifecycle.HiltViewModel
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
    private val getHomeStateUseCase: GetHomeStateUseCase,
) : BaseViewModel<GameState, GameIntent, GameNavigation>() {

    override val initialState: GameState get() = GameState()
    override fun OnEvent(event: GameIntent) {

        when (event) {
            is GameIntent.LeaveRoom -> {
                updateState {
                    it.copy(
                        currentPlayerCount = it.currentPlayerCount - 1,
                        joinedList = it.joinedList.filter { player -> player.userId != event.leaveRoom.userId }
                        ,
                        isLeft = true,

                        )
                }

                leaveRoomUseCase(event.leaveRoom.toDomain())
                navigate( GameNavigation.GameScreenToHomeScreen)
            }

            is GameIntent.Ready -> {
                readyUseCase(event.ready.toDomain())
                updateState { it.copy(isReady = true, isCancelled = false) }
            }

            is GameIntent.UnreadyUpdate -> {
                unreadyUpdateUseCase(event.unready.toDomain())
                updateState { it.copy(isReady = false, isCancelled = true) }
            }

            is GameIntent.GuessLetter -> {
                guessLetterUseCase(event.guessLetter.toDomain())
                val updatedLetters = state.value.letters.map { letter ->

                    if (event.guessLetter.letter == letter.char) {
                        letter.copy(
                            char = event.guessLetter.letter,
                            isSelected = true,
                        )
                    } else {
                        letter
                    }
                }
                updateState { s -> s.copy(letters = updatedLetters) }
            }
            is GameIntent.GuessWord -> guessWordUseCase(event.guessWord.toDomain())
            is GameIntent.InputText ->  updateState { it.copy(customWord = event.text) }
            is GameIntent.UpdateGame -> {
                updateState {
                    it.copy(
                        gameRouteUi = it.gameRouteUi.copy(
                            roomId = event.update.roomId,
                            roomName = event.update.roomName,
                            maxPlayers = event.update.maxPlayers,
                            difficulty = event.update.difficulty,
                            language = event.update.language,
                            userUid = event.update.userUid,
                            status = event.update.status, createdAt = event.update.createdAt,
                            username = event.update.username
                        ),

                        )

                }


            }

            GameIntent.ClearState -> clearState()
            is GameIntent.LetterClicked -> {}
            GameIntent.GetLetters -> getLetters()
            GameIntent.ReadyPlayerSheetVisibility -> updateState { it.copy(isReadyPlayerSheetOpen = !it.isReadyPlayerSheetOpen) }
            GameIntent.CustomWordVisibility ->  updateState { it.copy(isCustomWordVisible = !it.isCustomWordVisible) }
            GameIntent.GoBack ->  updateState { it.copy(isBack = !it.isBack) }
            GameIntent.ChangeWordVisibility -> changeWordVisibility()
            is GameIntent.GoToHistoryScreen -> TODO()
            GameIntent.GoToHomeScreen -> TODO()
        }
    }


    private var timerJob: Job? = null
    private var guessJob: Job? = null

    init {


        updateState {  GameState(
            letters = getInitialLetters(),
            isObservingFlows = false,

            ) }
        observeFlows()
        OnEvent(GameIntent.GetLetters)

    }



    private fun getInitialLetters(): List<LetterUi> {
        val alphabet = if (state.value.gameRouteUi.language == "az") {
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
        if(state.value.isObservingFlows) return
        updateState { it.copy(isObservingFlows = true) }
        playerJoinedFlowUseCase().onEach {
            if(it.userId != state.value.gameRouteUi.userUid){
                updateState { s ->
                    s.copy(
                        playerJoined = it.toUi(),
                        joinedList = s.joinedList + it.toUi(),
                        currentPlayerCount = s.currentPlayerCount + 1
                    )
                }
            }

        }.launchIn(viewModelScope)

        playerReadyUpdatedFlowUseCase().onEach { update ->
            updateState { s ->
                val newReadyList = s.joinedList.map { player ->
                    if (player.userId == update.userId) player.copy(isReady = update.ready) else player
                }

                s.copy(
                    playerReadyUpdated = update.toUi(),
                    joinedList = newReadyList
                )
            }
        }.launchIn(viewModelScope)

        playerLeftFlowUseCase().onEach {
           updateState { s ->
                s.copy(
                    playerLeft = it.toUi(),
                    joinedList = s.joinedList.filter { player -> player.userId != it.userId }
                    ,
                    currentPlayerCount = s.currentPlayerCount - 1
                )
            }
        }.launchIn(viewModelScope)

        gameStartedFlowUseCase().onEach {
            updateState { s -> s.copy(gameStarted = it.toUi(), isGameStarted = true,gameUpdateUi = GameUpdateUi(
                discovered = List(it.wordLength) { "_" },
                guessedBy = "",
                guessedLetter = "",
                correct = false,
                playerScore = 0
            )
            ) }
        }.launchIn(viewModelScope)

        turnFlowUseCase().onEach {

           updateState { s ->
                s.copy(
                    turn = it.toUI(),
                    isYourTurn = s.gameRouteUi.userUid == it.userId
                )
            }
            if (it.userId.isNotEmpty()) {
                startTurnTimer()
            }

        }.launchIn(viewModelScope)

        gameUpdateFlowUseCase().onEach { gameUpdate ->
           isGuessesVisible()

            val updatedLetters = state.value.letters.map { letter ->


                if (gameUpdate.guessedLetter.lowercase() == letter.char.lowercase()) {

                    letter.copy(
                        isSelected = true,
                        isCorrect = gameUpdate.correct
                    )
                } else {
                    letter
                }
            }

                updateState { s -> s.copy(guessingCounts = s.guessingCounts + 1) }


            if (!gameUpdate.correct) {
                updateState { s -> s.copy(totalWrongGuesses = s.totalWrongGuesses + 1) }
            }


            updateState { s -> s.copy(gameUpdateUi = s.gameUpdateUi?.copy(discovered = gameUpdate.discovered ?: s.gameUpdateUi.discovered , guessedBy = gameUpdate.guessedBy, guessedLetter = gameUpdate.guessedLetter, correct = gameUpdate.correct, playerScore = gameUpdate.playerScore), letters = updatedLetters) }

        }.launchIn(viewModelScope)


        gameSettingsFlowUseCase().onEach {
            updateState { s -> s.copy(gameSettings = it.toUi()) }
        }.launchIn(viewModelScope)

        playerEliminatedFlowUseCase().onEach {
            updateState { s -> s.copy(playerEliminated = it.toUi(), isGameOver =if(it.userId == state.value.gameRouteUi.userUid) true else false, joinedList = s.joinedList.filter { player -> player.userId != it.userId }) }
        }.launchIn(viewModelScope)

        gameOverFlowUseCase().onEach {
            updateState { s -> s.copy(gameOver = it.toUi()) }
        }.launchIn(viewModelScope)

        getHomeState()
    }





    fun changeWordVisibility(){
        updateState { it.copy(isWordVisible = !it.isWordVisible) }
    }


    private fun clearState() {
        timerJob?.cancel()
    }

    private fun startTurnTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            (30 downTo 0).forEach { second ->
                updateState { it.copy(turnTimer = second) }
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

        updateState { it.copy(letters = alphabet.map { LetterUi(it) }) }
    }


    fun isGuessesVisible(){
        guessJob?.cancel()

       guessJob =  viewModelScope.launch {
           updateState { it.copy(isGuessesVisible = true) }
           delay(2000L)
           updateState { it.copy(isGuessesVisible = false) }
        }

    }

    fun getHomeState() {
        viewModelScope.launch {
            getHomeStateUseCase().collect { home ->
                val homeState = home.toUi()
                if(homeState.roomId == state.value.gameRouteUi.roomId){
                    updateState {gameState -> gameState.copy(currentPlayerCount = gameState.currentPlayerCount + 1,joinedList =  gameState.joinedList + homeState.players.map {
                        PlayerJoinedUi(
                            it.id,
                            it.name,
                            it.ready
                        )
                    }) }

                }
            }

        }
    }


}