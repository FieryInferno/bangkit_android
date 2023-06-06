package com.example.bangkitandroid.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Blog (
    val image: String,
    val description: String,
    val id: Int,
    val title: String,
    val user: User,
    val timestamp: String
): Parcelable