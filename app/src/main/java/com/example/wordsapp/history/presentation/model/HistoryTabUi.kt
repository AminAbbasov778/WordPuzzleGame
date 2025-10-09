package com.example.wordsapp.history.presentation.model

import com.example.wordsapp.history.presentation.enums.HistoryTabs

data class HistoryTabUi(val statusName : String ,val  isSelected : Boolean = false,val status: HistoryTabs) {
}