package com.example.wordsapp.game

import com.example.wordsapp.home.domain.JoinRoomModel
import com.example.wordsapp.home.presentation.GameRouteUi
import com.example.wordsapp.home.presentation.HomeIntents
import com.example.wordsapp.home.presentation.HomeState

sealed class GameIntent {

    data class Ready(val ready: com.example.wordsapp.core.data.models.Ready) : GameIntent()
    data class UpdateGame(val update: GameRouteUi) : GameIntent()
    data class InputText(val text: String) : GameIntent()
    data class UnreadyUpdate(val unready: com.example.wordsapp.core.data.models.UnreadyUpdate) : GameIntent()
    data class LeaveRoom(val leaveRoom: com.example.wordsapp.core.data.models.LeaveRoom) : GameIntent()
    data class GuessLetter(val guessLetter: com.example.wordsapp.core.data.models.GuessLetter) : GameIntent()
    data class GuessWord(val guessWord: com.example.wordsapp.core.data.models.GuessWord) : GameIntent()
    data class LetterClicked(val letterUi: LetterUi) : GameIntent()
    object ClearState : GameIntent()
    object  GetLetters : GameIntent()
    object  GoBack : GameIntent()
    object  CustomWordVisibility : GameIntent()
    object ReadyPlayerSheetVisibility : GameIntent()
    object ChangeWordVisibility : GameIntent()


}