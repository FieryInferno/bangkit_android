package com.example.bangkitandroid.service

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatDateTime(dateTime: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    val date: Date = inputFormat.parse(dateTime) as Date
    return outputFormat.format(date)
}