package com.example.wordsapp.home.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wordsapp.R
import com.example.wordsapp.ui.theme.Inter

@Composable
fun EmptyHistory() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_empty_history),
            contentDescription = "log",
            tint = Color.Unspecified,
            modifier = Modifier.padding(top = 200.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "There is nothing here!",
            style = Inter.copy(color = Color(0xFFACAEB1).copy(0.8f), fontSize = 12.sp),
            textAlign = TextAlign.Center
        )
    }

}