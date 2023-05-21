package com.example.bangkitandroid.data.remote.response

import com.example.bangkitandroid.domain.entities.Disease
import com.google.gson.annotations.SerializedName

data class DiseaseHistoryResponse (
    @field:SerializedName("success")
    val success: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("data")
    val disease: List<Disease>?,
)