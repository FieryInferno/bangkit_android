package com.example.bangkitandroid.data.remote.response

import com.example.bangkitandroid.domain.entities.User
import com.google.gson.annotations.SerializedName

data class RegisterResponse (
    @field:SerializedName("success")
    val success: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("data")
    val data: RegisterResult? = null,
)

data class RegisterResult(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("userId")
    val phoneNumber: String? = null,

    @field:SerializedName("token")
    val token: String? = null
)