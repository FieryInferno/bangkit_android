package com.example.bangkitandroid.data.remote.response

import com.google.gson.annotations.SerializedName

data class TemplateResponse(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String,
)