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
data class DataUser(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("phone_number")
    val phoneNumber: String,

    @field:SerializedName("email")
    val email: Any? = null
)
data class LoginResult(

    @field:SerializedName("token")
    val token: String,

    @field:SerializedName("refresh_token")
    val refreshToken: String,

    @field:SerializedName("user")
    val user: DataUser,
)