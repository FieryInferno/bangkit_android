package com.example.bangkitandroid.domain.entities

data class User(
    val id: Int,
    val name: String,
    val imgUrl: String,
    val phoneNumber: String,
    val password: String
)