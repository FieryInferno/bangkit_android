package com.example.bangkitandroid.data.remote.model

import com.google.gson.annotations.SerializedName

data class UserModel(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("phone_number")
	val phoneNumber: String,

	@field:SerializedName("email")
	val email: Any
)
