package com.example.bangkitandroid.domain.entities

import com.google.gson.annotations.SerializedName

data class Blog (
    val image: String,
    val description: String,
    val id: Int,
    val title: String,
    val user: User,
    val timestamp: String
)