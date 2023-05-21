package com.example.bangkitandroid.data.remote.response

import com.example.bangkitandroid.domain.entities.Disease
import com.google.gson.annotations.SerializedName

data class DiseaseHistoryResponse (
    @field:SerializedName("status")
    val status: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("data")
    val diseases: List<Disease>,
)