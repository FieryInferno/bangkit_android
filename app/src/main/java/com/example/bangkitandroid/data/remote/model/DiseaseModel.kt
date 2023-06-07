package com.example.bangkitandroid.data.remote.model

import com.google.gson.annotations.SerializedName

data class DiseaseModel(

	@field:SerializedName("solution")
	val solution: String,

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("products")
	val products: List<ProductModel>
)