package com.example.bangkitandroid.data.remote.response

import com.example.bangkitandroid.data.remote.model.UserModel
import com.google.gson.annotations.SerializedName

data class UserResponse(

	@field:SerializedName("data")
	val user: UserModel,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)
