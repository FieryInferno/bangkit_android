package com.example.bangkitandroid.data.remote.response

import com.example.bangkitandroid.domain.entities.Blog
import com.example.bangkitandroid.domain.entities.History
import com.google.gson.annotations.SerializedName

data class HomeResponse(

	@field:SerializedName("is_authenticated")
	val isAuthenticated: Boolean,

	@field:SerializedName("history")
	val history: List<History>,

	@field:SerializedName("blogs")
	val blogs: List<Blog>
)
