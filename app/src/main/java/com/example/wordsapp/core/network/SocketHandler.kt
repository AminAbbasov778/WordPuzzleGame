package com.example.wordsapp.core.network

import android.util.Log
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
import com.example.wordsapp.core.data.models.RoomUpdate
import com.example.wordsapp.core.data.models.Turn
import com.example.wordsapp.core.data.models.UnreadyUpdate
import com.google.gson.Gson
import io.socket.client.Socket
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SocketHandler @Inject constructor(
    private val socket: Socket,
) {
    private val gson = Gson()

    private val _playerJoinedFlow = MutableSharedFlow<PlayerJoined>(replay = 0)
    val playerJoinedFlow: SharedFlow<PlayerJoined> = _playerJoinedFlow

    private val _playerReadyUpdatedFlow = MutableSharedFlow<PlayerReadyUpdated>(replay = 0)
    val playerReadyUpdatedFlow: SharedFlow<PlayerReadyUpdated> = _playerReadyUpdatedFlow

    private val _playerLeftFlow = MutableSharedFlow<PlayerLeft>(replay = 0)
    val playerLeftFlow: SharedFlow<PlayerLeft> = _playerLeftFlow

    private val _gameStartedFlow = MutableSharedFlow<GameStarted>(replay = 0)
    val gameStartedFlow: SharedFlow<GameStarted> = _gameStartedFlow

    private val _turnFlow = MutableSharedFlow<Turn>(replay = 0)
    val turnFlow: SharedFlow<Turn> = _turnFlow

    private val _gameUpdateFlow = MutableSharedFlow<GameUpdate>(replay = 0)
    val gameUpdateFlow: SharedFlow<GameUpdate> = _gameUpdateFlow

    private val _gameSettingsFlow = MutableSharedFlow<GameSettingsUpdate>(replay = 0)
    val gameSettingsFlow: SharedFlow<GameSettingsUpdate> = _gameSettingsFlow

    private val _playerEliminatedFlow = MutableSharedFlow<PlayerEliminated>(replay = 0)
    val playerEliminatedFlow: SharedFlow<PlayerEliminated> = _playerEliminatedFlow

    private val _gameOverFlow = MutableSharedFlow<GameOver>(replay = 0)
    val gameOverFlow: SharedFlow<GameOver> = _gameOverFlow

    private val _roomUpdateFlow = MutableSharedFlow<RoomUpdate>(replay = 0)
    val roomUpdateFlow: SharedFlow<RoomUpdate> = _roomUpdateFlow


    private val _roomStateFlow = MutableSharedFlow<RoomState>(replay = 0)
    val roomSateFlow: SharedFlow<RoomState> = _roomStateFlow

    private var isConnected = false


    fun connect() {
        if (isConnected && socket.connected()) return
        socket.io().reconnection(true).reconnectionAttempts(5).reconnectionDelay(1000)
        socket.connect()
        Log.e("SocketHandler", "Socket connecting...")
        setupListeners()
    }

    private fun setupListeners() {
        socket.off() // Clear all existing listeners to prevent duplicates
        socket.on(Socket.EVENT_CONNECT) {
            Log.e("SocketHandler", "Socket connected (EVENT_CONNECT)")
            isConnected = true
            registerEventListeners()
        }
        socket.on(Socket.EVENT_DISCONNECT) {
            Log.e("SocketHandler", "Socket disconnected")
            isConnected = false
        }
    }

    private fun registerEventListeners() {
        listenPlayerJoined()
        listenPlayerReadyUpdated()
        listenPlayerLeft()
        listenGameStarted()
        listenTurn()
        listenGameUpdate()
        listenGameSettingsUpdate()
        listenPlayerEliminated()
        listenGameOver()
        listenRoomUpdate()
        listenRoomState()
    }

    fun disconnect() {
        socket.off()
        socket.disconnect()
        isConnected = false
        Log.e("SocketHandler", "Socket disconnected")
    }

    fun emitToken(token: String) = socket.emit("token", token)

    fun listenPlayerJoined() {
        socket.on("playerJoined") { args ->
            args.getOrNull(0)?.let { json ->
                CoroutineScope(Dispatchers.IO).launch {
                    _playerJoinedFlow.emit(gson.fromJson(json.toString(), PlayerJoined::class.java))
                    Log.e("SocketHandler", "playerJoined: ${_playerJoinedFlow.toString()}")

                }
            }
        }
    }

    fun listenPlayerReadyUpdated() = socket.on("playerReadyUpdated") { args ->
        args.getOrNull(0)?.let { json ->
            CoroutineScope(Dispatchers.IO).launch {
                Log.e("SocketHandler", "listenPlayerReadyUpdated: $json")
                _playerReadyUpdatedFlow.emit(
                    gson.fromJson(
                        json.toString(),
                        PlayerReadyUpdated::class.java
                    )
                )
                Log.e(
                    "SocketHandler",
                    "listenPlayerReadyUpdated: ${_playerReadyUpdatedFlow.toString()}"
                )

            }
        }
    }


    fun listenPlayerLeft() = socket.on("playerLeft") { args ->

        Log.e("SocketHandler", "playerLeft: $args")
        args.getOrNull(0)?.let { json ->
            CoroutineScope(Dispatchers.IO).launch {
                _playerLeftFlow.emit(gson.fromJson(json.toString(), PlayerLeft::class.java))
            }
        }
    }

    fun listenRoomState() = socket.on("roomState") { args ->
        Log.e("SocketHandler", "roomState: $args")
        args.getOrNull(0)?.let { json ->
            Log.e("SocketHandler", "roomStateJson: ${json.toString()}")

            CoroutineScope(Dispatchers.IO).launch {
                _roomStateFlow.emit(gson.fromJson(json.toString(), RoomState::class.java))
                Log.e("SocketHandler", "roomStateJson: ${_roomStateFlow.toString()}")

            }
        }
    }

    fun listenGameStarted() = socket.on("gameStarted") { args ->
        Log.e("SocketHandler", "gamestaretded: $args")

        args.getOrNull(0)?.let { json ->
            CoroutineScope(Dispatchers.IO).launch {
                _gameStartedFlow.emit(gson.fromJson(json.toString(), GameStarted::class.java))
            }
        }
    }

    fun listenTurn() = socket.on("turn") { args ->

        Log.e("SocketHandler", "turn: $args")

        args.getOrNull(0)?.let { json ->
            CoroutineScope(Dispatchers.IO).launch {
                _turnFlow.emit(gson.fromJson(json.toString(), Turn::class.java))
            }
        }
    }

    fun listenGameUpdate() = socket.on("gameUpdate") { args ->
        Log.e("SocketHandler", "gameUpdate: $args")
        args.getOrNull(0)?.let { json ->
            CoroutineScope(Dispatchers.IO).launch {
                _gameUpdateFlow.emit(gson.fromJson(json.toString(), GameUpdate::class.java))
            }
        }
    }

    fun listenGameSettingsUpdate() = socket.on("gameSettingsUpdate") { args ->

        Log.e("SocketHandler", "gameSettingsUpdate: $args")
        args.getOrNull(0)?.let { json ->
            CoroutineScope(Dispatchers.IO).launch {
                _gameSettingsFlow.emit(
                    gson.fromJson(
                        json.toString(),
                        GameSettingsUpdate::class.java
                    )
                )
            }
        }
    }

    fun listenPlayerEliminated() = socket.on("playerEliminated") { args ->

        Log.e("SocketHandler", "playerEliminated: $args")
        args.getOrNull(0)?.let { json ->
            CoroutineScope(Dispatchers.IO).launch {
                _playerEliminatedFlow.emit(
                    gson.fromJson(
                        json.toString(),
                        PlayerEliminated::class.java
                    )
                )
            }
        }
    }



    fun listenGameOver() = socket.on("gameOver") { args ->

        Log.e("SocketHandler", "gameOver: $args")
        args.getOrNull(0)?.let { json ->
            CoroutineScope(Dispatchers.IO).launch {
                _gameOverFlow.emit(gson.fromJson(json.toString(), GameOver::class.java))
            }
        }
    }

    fun listenRoomUpdate() = socket.on("roomUpdate") { args ->
        args.getOrNull(0)?.let { json ->
            CoroutineScope(Dispatchers.IO).launch {
                Log.e("SocketHandler", "Room update received: $json")
                _roomUpdateFlow.emit(gson.fromJson(json.toString(), RoomUpdate::class.java))
                Log.e("SocketHandler", "listenRoomUpdate: ${_roomUpdateFlow.replayCache}")
            }
        }
    }

    fun joinRoom(joinRoom: JoinRoom) {
        Log.e("SocketHandler", "Room update received: ${joinRoom.toString()}")


        val payload = JSONObject().apply {
            put("roomId", joinRoom.roomId)
            put("userId", joinRoom.userId)
            put("username", joinRoom.username)
            put("difficulty", joinRoom.difficulty)
            put("language", joinRoom.language)
        }
        socket.emit("joinRoom", payload)
    }

    fun ready(ready: Ready) {

        Log.e("SocketHandler", "ready${ready.toString()}")

        val payload = JSONObject().apply {
            put("roomId", ready.roomId)
            put("userId", ready.userId)
            put("difficulty", ready.difficulty)
            put("language", ready.language)
        }
        socket.emit("ready", payload)
    }

    fun unreadyUpdate(unready: UnreadyUpdate) {
        Log.e("SocketHandler", "unreadyUpdate: ")
        val payload = JSONObject().apply {
            put("roomId", unready.roomId)
            put("userId", unready.userId)
        }
        socket.emit("unready", payload)
    }

    fun leaveRoom(leaveRoom: LeaveRoom) {
        Log.e("SocketHandler", "leaveRoom: ${leaveRoom.toString()}")
        val payload = JSONObject().apply {
            put("roomId", leaveRoom.roomId)
            put("userId", leaveRoom.userId)
        }
        socket.emit("leaveRoom", payload)
    }

    fun guessLetter(guessLetter: GuessLetter) {
        Log.e("SocketHandler", "guessLetter: ${guessLetter.toString()}")
        val payload = JSONObject().apply {
            put("roomId", guessLetter.roomId)
            put("userId", guessLetter.userId)
            put("letter", guessLetter.letter)
        }
        socket.emit("guessLetter", payload)
    }

    fun guessWord(guessWord: GuessWord) {
        Log.e("SocketHandler", "guessWord: ${guessWord.toString()}")
        val payload = JSONObject().apply {
            put("roomId", guessWord.roomId)
            put("userId", guessWord.userId)
            put("guess", guessWord.guess)
        }
        socket.emit("guessWord", payload)


    }
}
