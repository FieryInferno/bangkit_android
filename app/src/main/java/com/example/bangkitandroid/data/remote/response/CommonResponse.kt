package com.example.bangkitandroid.data.remote.response

import com.google.gson.annotations.SerializedName

data class CommonResponse (
    @field:SerializedName("status")
    val status: Boolean,

    @field:SerializedName("message")
    val message: String,
)