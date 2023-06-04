package com.example.bangkitandroid.data.remote.response

import com.example.bangkitandroid.data.remote.model.DiseaseModel
import com.example.bangkitandroid.data.remote.model.HistoryModel
import com.google.gson.annotations.SerializedName

data class DiseaseResponse(

	@field:SerializedName("data")
	val data: HistoryModel,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("statusCode")
	val statusCode: Int
)
