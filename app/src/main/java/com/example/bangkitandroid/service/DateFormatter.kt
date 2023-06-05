package com.example.bangkitandroid.service

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object DateFormatter {
    @RequiresApi(Build.VERSION_CODES.O)
    fun formatDate(currentDateString: String, targetTimeZone: String): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX")
        val offsetDateTime = OffsetDateTime.parse(currentDateString, formatter)
        val formatterOutput = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm")
            .withZone(ZoneId.of(targetTimeZone))
        return formatterOutput.format(offsetDateTime)
    }
}