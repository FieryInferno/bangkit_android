package com.example.bangkitandroid.domain.entities

data class History(
    val image: String?,
    val disease: Disease,
    val id: Int,
    val user: Int,
    val timestamp: String
)