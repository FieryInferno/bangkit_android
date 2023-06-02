package com.example.bangkitandroid.data.remote.response

import com.example.bangkitandroid.data.remote.model.DiseaseModel
import com.google.gson.annotations.SerializedName

data class DiseaseHistoryResponse(

	@field:SerializedName("result")
	val result: List<DiseaseResponse>,

	@field:SerializedName("meta")
	val meta: Meta
)

data class Meta(

	@field:SerializedName("total_item")
	val totalItem: String,

	@field:SerializedName("has_previous")
	val hasPrevious: Boolean,

	@field:SerializedName("limit")
	val limit: String,

	@field:SerializedName("has_next")
	val hasNext: Boolean,

	@field:SerializedName("total_pages")
	val totalPages: String,

	@field:SerializedName("current_page")
	val currentPage: String
)
