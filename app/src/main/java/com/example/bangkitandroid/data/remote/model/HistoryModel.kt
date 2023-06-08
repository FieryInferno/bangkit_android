package com.example.bangkitandroid.data.remote.model

import com.google.gson.annotations.SerializedName

data class HistoryModel(

    @field:SerializedName("image")
    val image: String?,

    @field:SerializedName("disease")
    val disease: DiseaseModel,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("user")
    val user: Int,

    @field:SerializedName("timestamp")
    val timestamp: String
)