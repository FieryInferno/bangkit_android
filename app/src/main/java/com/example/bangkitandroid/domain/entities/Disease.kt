package com.example.bangkitandroid.domain.entities

data class Disease (
    val id: Int,
    val title: String,
    val dateTime: String,
    val imgUrl: String,
    val description: String,
    val treatment: String,
    val products: List<Product>
    )