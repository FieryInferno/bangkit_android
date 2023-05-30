package com.example.bangkitandroid.domain.entities

import com.google.gson.annotations.SerializedName

data class Blog (

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("image")
    val imgUrl: String,

    @field:SerializedName("timestamp")
    val dateTime: String,

    @field:SerializedName("user")
    val user: Int,
)