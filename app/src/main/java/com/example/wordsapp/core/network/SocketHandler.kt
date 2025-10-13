package com.example.wordsapp.core.network

import com.example.wordsapp.core.network.base.BaseSocketHandler
import com.example.wordsapp.game.data.model.HomeState
import com.example.wordsapp.home.data.model.RoomUpdate
import com.example.wordsapp.game.data.model.GameOver
import com.example.wordsapp.game.data.model.GameSettingsUpdate
import com.example.wordsapp.game.data.model.GameStarted
import com.example.wordsapp.game.data.model.GameUpdate
import com.example.wordsapp.game.data.model.GuessLetter
import com.example.wordsapp.game.data.model.GuessWord
import com.example.wordsapp.home.data.model.JoinRoom
import com.example.wordsapp.game.data.model.LeaveRoom
import com.example.wordsapp.game.data.model.PlayerEliminated
import com.example.wordsapp.game.data.model.PlayerJoined
import com.example.wordsapp.game.data.model.PlayerLeft
import com.example.wordsapp.game.data.model.PlayerReadyUpdated
import com.example.wordsapp.game.data.model.Ready
import com.example.wordsapp.game.data.model.Turn
import com.example.wordsapp.game.data.model.UnreadyUpdate
import com.google.gson.Gson
import io.socket.client.Socket
import kotlinx.coroutines.flow.SharedFlow
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SocketHandler @Inject constructor(
    socket: Socket,
    gson: Gson
) : BaseSocketHandler(socket, gson) {

    val playerJoinedFlow: SharedFlow<PlayerJoined> = listen("playerJoined", PlayerJoined::class.java)
    val playerReadyUpdatedFlow: SharedFlow<PlayerReadyUpdated> = listen("playerReadyUpdated", PlayerReadyUpdated::class.java)
    val playerLeftFlow: SharedFlow<PlayerLeft> = listen("playerLeft", PlayerLeft::class.java)
    val gameStartedFlow: SharedFlow<GameStarted> = listen("gameStarted", GameStarted::class.java)
    val turnFlow: SharedFlow<Turn> = listen("turn", Turn::class.java)
    val gameUpdateFlow: SharedFlow<GameUpdate> = listen("gameUpdate", GameUpdate::class.java)
    val gameSettingsFlow: SharedFlow<GameSettingsUpdate> = listen("gameSettingsUpdate", GameSettingsUpdate::class.java)
    val playerEliminatedFlow: SharedFlow<PlayerEliminated> = listen("playerEliminated", PlayerEliminated::class.java)
    val gameOverFlow: SharedFlow<GameOver> = listen("gameOver", GameOver::class.java)
    val roomUpdateFlow: SharedFlow<RoomUpdate> = listen("roomUpdate", RoomUpdate::class.java)
    val homeStateFlow: SharedFlow<HomeState> = listen("roomState", HomeState::class.java)

    fun joinRoom(joinRoom: JoinRoom) = emit("joinRoom", JSONObject().apply {
        put("roomId", joinRoom.roomId)
        put("userId", joinRoom.userId)
        put("username", joinRoom.username)
        put("difficulty", joinRoom.difficulty)
        put("language", joinRoom.language)
    })
    fun ready(ready: Ready) = emit("ready", JSONObject().apply {
        put("roomId", ready.roomId)
        put("userId", ready.userId)
        put("difficulty", ready.difficulty)
        put("language", ready.language)
    })
    fun unreadyUpdate(unready: UnreadyUpdate) = emit("unready", JSONObject().apply {
        put("roomId", unready.roomId)
        put("userId", unready.userId)
    })
    fun leaveRoom(leaveRoom: LeaveRoom) = emit("leaveRoom", JSONObject().apply {
        put("roomId", leaveRoom.roomId)
        put("userId", leaveRoom.userId)
    })
    fun guessLetter(guessLetter: GuessLetter) = emit("guessLetter", JSONObject().apply {
        put("roomId", guessLetter.roomId)
        put("userId", guessLetter.userId)
        put("letter", guessLetter.letter)
    })
    fun guessWord(guessWord: GuessWord) = emit("guessWord", JSONObject().apply {
        put("roomId", guessWord.roomId)
        put("userId", guessWord.userId)
        put("guess", guessWord.guess)
    })
}
