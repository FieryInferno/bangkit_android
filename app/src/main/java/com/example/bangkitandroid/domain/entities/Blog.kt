package com.example.bangkitandroid.domain.entities

data class Blog (
    val id: Int,
    val title: String,
    val dateTime: String,
    val author: String,
    val imgUrl: String,
    val description: String,
    val comments: List<Comment>

)