package com.example.bangkitandroid.data.remote.model

import com.google.gson.annotations.SerializedName

data class BlogModel(

    @field:SerializedName("image")
    val image: String,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("user")
    val user: Int,

    @field:SerializedName("timestamp")
    val timestamp: String
)
