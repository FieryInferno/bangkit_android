package com.example.bangkitandroid.data.remote.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse (
    @field:SerializedName("success")
    val success: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("data")
    val data: RegisterResult,
)

data class RegisterResult(

    @field:SerializedName("password")
    val password: String,

    @field:SerializedName("is_superuser")
    val isSuperuser: Boolean,

    @field:SerializedName("is_active")
    val isActive: Boolean,

    @field:SerializedName("user_permissions")
    val userPermissions: List<Any?>,

    @field:SerializedName("is_staff")
    val isStaff: Boolean,

    @field:SerializedName("last_login")
    val lastLogin: Any,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("groups")
    val groups: List<Any?>,

    @field:SerializedName("phone_number")
    val phoneNumber: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("email")
    val email: Any,

    @field:SerializedName("image")
    val image: Any
)