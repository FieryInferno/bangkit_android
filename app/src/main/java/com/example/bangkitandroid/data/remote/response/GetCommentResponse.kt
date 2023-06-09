package com.example.bangkitandroid.data.remote.response

import com.example.bangkitandroid.data.remote.model.CommentModel
import com.example.bangkitandroid.data.remote.model.MetaModel
import com.example.bangkitandroid.data.remote.model.UserModel
import com.google.gson.annotations.SerializedName

data class GetCommentResponse(

	@field:SerializedName("result")
	val result: List<CommentModel>,

	@field:SerializedName("meta")
	val meta: MetaModel
)
