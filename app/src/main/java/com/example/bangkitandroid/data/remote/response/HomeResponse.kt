package com.example.bangkitandroid.data.remote.response

import com.example.bangkitandroid.data.remote.model.BlogModel
import com.example.bangkitandroid.data.remote.model.HistoryModel
import com.google.gson.annotations.SerializedName

data class HomeResponse(

	@field:SerializedName("blogs")
	val blogs: List<BlogModel>,

	@field:SerializedName("is_authenticated")
	val isAuthenticated: Boolean,

	@field:SerializedName("history")
	val history: List<HistoryModel>
)

