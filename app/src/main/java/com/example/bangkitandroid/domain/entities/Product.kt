package com.example.bangkitandroid.domain.entities

import com.google.gson.annotations.SerializedName

data class Product (

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("url")
    val url: String,

    @field:SerializedName("price")
    val price: Int,

    @field:SerializedName("image")
    val imgUrl: String,

    @field:SerializedName("created_at")
    val createdAt: String,

    @field:SerializedName("updated_at")
    val updatedAt: String,
)