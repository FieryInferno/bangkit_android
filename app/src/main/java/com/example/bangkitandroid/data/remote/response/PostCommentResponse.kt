package com.example.bangkitandroid.data.remote.response

import com.example.bangkitandroid.data.remote.model.CommentModel
import com.example.bangkitandroid.data.remote.model.UserModel
import com.example.bangkitandroid.domain.entities.User
import com.google.gson.annotations.SerializedName

data class PostCommentResponse(

	@field:SerializedName("data")
	val data: CommentModel,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)
