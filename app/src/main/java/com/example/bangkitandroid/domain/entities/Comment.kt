package com.example.bangkitandroid.domain.entities

data class Comment(
    val id: Int,
    val user: User,
    val dateTime: String,
    val description: String
)