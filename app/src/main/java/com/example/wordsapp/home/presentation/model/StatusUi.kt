package com.example.wordsapp.home.presentation.model

import com.example.wordsapp.core.presentation.enums.Status

data class StatusUi(val statusName : String ,val  isSelected : Boolean = false,val status: Status) {
}