package com.example.wordsapp.core.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.util.concurrent.TimeUnit
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun getRelativeTime(timestamp: Timestamp?): String {
    if (timestamp == null) return ""
    val now = System.currentTimeMillis()
    val time = timestamp.toDate().time
    val diff = now - time

    val minutes = TimeUnit.MILLISECONDS.toMinutes(diff)
    val hours = TimeUnit.MILLISECONDS.toHours(diff)
    val days = TimeUnit.MILLISECONDS.toDays(diff)

    return when {
        minutes < 1 -> "Just now"
        minutes < 60 -> "${minutes}m ago"
        hours < 24 -> "${hours}h ago"
        else -> "${days}d ago"
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatDate(input: String): String {
    return try {
        val parsed = ZonedDateTime.parse(input, DateTimeFormatter.ISO_ZONED_DATE_TIME)
        val formatter = DateTimeFormatter.ofPattern("MMM dd, HH:mm", Locale.ENGLISH)
        parsed.format(formatter)
    } catch (e: DateTimeParseException) {
        try {
            val parsed = LocalDateTime.parse(input, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            val formatter = DateTimeFormatter.ofPattern("MMM dd, HH:mm", Locale.ENGLISH)
            parsed.format(formatter)
        } catch (e2: Exception) {
            input
        }
    }
}



fun getTimeAgo(seconds: Long): String {
    val now = System.currentTimeMillis() / 1000
    val diff = now - seconds

    return when {
        diff < 60 -> "Just now"
        diff < 3600 -> "${diff / 60}m ago"
        diff < 86400 -> "${diff / 3600}h ago"
        diff < 2592000 -> "${diff / 86400}d ago"
        else -> {
            val date = Date(seconds * 1000)
            val currentYear = Calendar.getInstance().get(Calendar.YEAR)
            val targetYear = Calendar.getInstance().apply { time = date }.get(Calendar.YEAR)

            val format = if (currentYear == targetYear)
                SimpleDateFormat("MMM d", Locale.getDefault())
            else
                SimpleDateFormat("MMM d, yyyy", Locale.getDefault())

            format.format(date)
        }
    }
}

