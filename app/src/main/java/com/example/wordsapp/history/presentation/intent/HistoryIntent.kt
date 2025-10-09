package com.example.wordsapp.history.presentation.intent

import com.example.wordsapp.core.presentation.base.UIIntent
import com.example.wordsapp.history.presentation.enums.HistoryTabs

sealed class HistoryIntent : UIIntent {
    data class GetGames(val userId: String) : HistoryIntent()
    data class GetGameDetail(val gameId: String) : HistoryIntent()
    object GetLeaderboard : HistoryIntent()
    data class ChangeWordVisibility(val gameId: String) : HistoryIntent()
    object ChangeWordVisibilityInDetail : HistoryIntent()
    object ChangeGameDetailVisibility : HistoryIntent()
    object GoToHome : HistoryIntent()
    data class GetUser(val userId: String) : HistoryIntent()
    data class GameCLicked(val gameId: String) : HistoryIntent()
    data class ChangeTab(val tab : HistoryTabs) : HistoryIntent()

}