package com.example.bangkitandroid.domain.entities

import com.google.gson.annotations.SerializedName

data class Disease (
    val id: Int,
    val products: List<Product>,
    val title: String,
    val description: String,
    val image: String,
    val treatment: String,
    val createdAt: String,
    val updatedAt: String,
)