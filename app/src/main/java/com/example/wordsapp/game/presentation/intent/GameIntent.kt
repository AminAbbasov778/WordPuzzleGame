package com.example.wordsapp.game.presentation.intent

import com.example.wordsapp.core.presentation.base.UIIntent
import com.example.wordsapp.game.presentation.model.GuessLetterUi
import com.example.wordsapp.game.presentation.model.GuessWordUi
import com.example.wordsapp.game.presentation.model.LeaveRoomUi
import com.example.wordsapp.game.presentation.model.LetterUi
import com.example.wordsapp.game.presentation.model.ReadyUi
import com.example.wordsapp.game.presentation.model.UnreadyUpdateUi
import com.example.wordsapp.history.presentation.model.HistoryRouteUi
import com.example.wordsapp.home.presentation.model.GameRouteUi

sealed class GameIntent : UIIntent {

    data class Ready(val ready: ReadyUi) : GameIntent()
    data class UpdateGame(val update: GameRouteUi) : GameIntent()
    data class InputText(val text: String) : GameIntent()
    data class UnreadyUpdate(val unready: UnreadyUpdateUi) : GameIntent()
    data class LeaveRoom(val leaveRoom: LeaveRoomUi) : GameIntent()
    data class GuessLetter(val guessLetter: GuessLetterUi) : GameIntent()
    data class GuessWord(val guessWord: GuessWordUi) : GameIntent()
    data class LetterClicked(val letterUi: LetterUi) : GameIntent()
    object ClearState : GameIntent()
    object  GetLetters : GameIntent()
    object  GoBack : GameIntent()
    object  CustomWordVisibility : GameIntent()
    object ReadyPlayerSheetVisibility : GameIntent()
    object ChangeWordVisibility : GameIntent()
    data class GoToHistoryScreen(val historyRouteUi: HistoryRouteUi) : GameIntent()
    object GoToHomeScreen : GameIntent()


}