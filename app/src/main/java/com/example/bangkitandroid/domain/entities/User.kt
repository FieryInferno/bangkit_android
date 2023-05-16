package com.example.bangkitandroid.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Int,
    val name: String,
    val imgUrl: String,
    val phoneNumber: String,
    val password: String
): Parcelable