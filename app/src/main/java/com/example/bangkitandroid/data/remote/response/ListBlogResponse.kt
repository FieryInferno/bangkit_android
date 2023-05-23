package com.example.bangkitandroid.data.remote.response

import com.example.bangkitandroid.domain.entities.Blog
import com.google.gson.annotations.SerializedName

data class ListBlogResponse (
    @field:SerializedName("status")
    val status: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("data")
    val blogs: List<Blog>,
)