package com.example.bangkitandroid.data.remote.response

import com.example.bangkitandroid.data.remote.model.DiseaseModel
import com.google.gson.annotations.SerializedName

data class DiseaseResponse(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("statusCode")
	val statusCode: Int
)

data class Data(

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("disease")
	val disease: DiseaseModel,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("user")
	val user: Int,

	@field:SerializedName("timestamp")
	val timestamp: String
)
