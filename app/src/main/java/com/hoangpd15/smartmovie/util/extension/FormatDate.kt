package com.hoangpd15.smartmovie.util.extension

import androidx.compose.runtime.Composable
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun formatDate(dateString: String?): String {
    return dateString?.let {
        try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            val date = inputFormat.parse(it)
            outputFormat.format(date)
        } catch (e: Exception) {
            "Invalid date"
        }
    } ?: "Date is null"
}