package com.example.wordsapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.wordsapp.R

// Set of Material typography styles to start with
val InknutFont = FontFamily(
    Font(R.font.inknutantiqua_semibold, FontWeight.SemiBold,),
    )
val InterFont = FontFamily(
    Font(R.font.inter_semibold, FontWeight.SemiBold,),
)


val Inknut40 = TextStyle(fontFamily = InknutFont, fontSize = 40.sp, fontWeight = FontWeight.SemiBold)
val Inter = TextStyle(fontFamily = InterFont, fontWeight = FontWeight.SemiBold)

