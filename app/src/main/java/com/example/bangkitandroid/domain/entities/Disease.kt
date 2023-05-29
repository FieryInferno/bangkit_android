package com.example.bangkitandroid.domain.entities

import com.google.gson.annotations.SerializedName

data class Disease (

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("products")
    val products: List<Product>,

    @field:SerializedName("name")
    val title: String,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("solution")
    val treatment: String,

    @field:SerializedName("created_at")
    val createdAt: String,

    @field:SerializedName("updated_at")
    val updatedAt: String,
)