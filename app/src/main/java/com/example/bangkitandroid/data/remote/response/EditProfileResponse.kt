package com.example.bangkitandroid.data.remote.response

import com.example.bangkitandroid.data.remote.model.UserModel
import com.google.gson.annotations.SerializedName

data class EditProfileResponse(

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("data")
	val user: UserModel,
)
