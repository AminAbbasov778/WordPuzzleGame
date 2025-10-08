package com.example.wordsapp.history.presentation

sealed class HistoryIntent {
    object GetGames : HistoryIntent()
    data class GetGameDetail(val gameId: String) : HistoryIntent()
    object GetLeaderboard : HistoryIntent()
    data class ChangeWordVisibility(val gameId: String) : HistoryIntent()
    object ChangeWordVisibilityInDetail : HistoryIntent()
    object ChangeGameDetailVisibility : HistoryIntent()
    data class GetUser(val userId: String) : HistoryIntent()
    data class GameCLicked(val gameId: String) : HistoryIntent()
    data class ChangeTab(val tab : HistoryTabs) : HistoryIntent()
}

