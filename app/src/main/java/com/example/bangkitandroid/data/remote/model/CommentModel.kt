package com.example.bangkitandroid.data.remote.model

import com.google.gson.annotations.SerializedName

data class CommentModel(

    @field:SerializedName("blog_id")
    val blogId: Int,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("user")
    val user: UserModel,

    @field:SerializedName("timestamp")
    val timestamp: String
)