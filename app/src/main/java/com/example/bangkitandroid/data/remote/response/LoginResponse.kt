package com.example.bangkitandroid.data.remote.response

import com.example.bangkitandroid.domain.entities.User
import com.google.gson.annotations.SerializedName

class LoginResponse (
    @field:SerializedName("success")
    val success: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("data")
    val data: LoginResult,
)

data class LoginResult(

    @field:SerializedName("token")
    val token: String,

    @field:SerializedName("refresh_token")
    val refreshToken: String,
)