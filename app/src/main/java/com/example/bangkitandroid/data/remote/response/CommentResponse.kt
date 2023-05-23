package com.example.bangkitandroid.data.remote.response

import com.example.bangkitandroid.domain.entities.Comment
import com.google.gson.annotations.SerializedName

data class CommentResponse (
    @field:SerializedName("success")
    val success: Boolean?,

    @field:SerializedName("message")
    val message: String?,

    @field:SerializedName("data")
    val comments: Comment?,
)