package com.example.bangkitandroid.domain.entities

import com.google.gson.annotations.SerializedName

data class History(

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("disease")
    val disease: Disease,

    @field:SerializedName("image")
    val image: String,

    @field:SerializedName("timestamp")
    val timestamp: String,

    @field:SerializedName("user")
    val user: Int,
)