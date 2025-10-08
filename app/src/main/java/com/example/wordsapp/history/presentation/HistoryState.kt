package com.example.wordsapp.history.presentation

import com.example.wordsapp.history.data.models.Leaderboard.Leaderboard
import com.example.wordsapp.history.data.models.Leaderboard.LeaderboardX
import com.example.wordsapp.history.data.models.history.Game
import com.example.wordsapp.history.data.models.user.User
import com.example.wordsapp.history.presentation.gamedetail.GameDetailUi
import com.example.wordsapp.history.presentation.history.GameUi
import com.example.wordsapp.history.presentation.leaderboard.LeaderboardUi

data class HistoryState(val isLoading: Boolean = false,
                        val gameHistory: List<GameUi> = emptyList(),
                        val gameDetail: GameDetailUi? = null,
                        val leaderboard: List<LeaderboardUi> = emptyList(),
                        val user : User? = null,
                        val error: String = "",
    val isGameDetailVisible : Boolean = false,
    val currentTab : HistoryTabs = HistoryTabs.HISTORY


                        ) {
}