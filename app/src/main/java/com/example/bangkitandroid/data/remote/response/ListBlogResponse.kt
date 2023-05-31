package com.example.bangkitandroid.data.remote.response

import com.example.bangkitandroid.domain.entities.Blog
import com.google.gson.annotations.SerializedName

data class ListBlogResponse (
    @field:SerializedName("result")
    val result: List<BlogResponse>,

    @field:SerializedName("meta")
    val meta: Meta? = null
)

data class Meta(

    @field:SerializedName("total_item")
    val totalItem: String? = null,

    @field:SerializedName("has_previous")
    val hasPrevious: Boolean? = null,

    @field:SerializedName("limit")
    val limit: String? = null,

    @field:SerializedName("has_next")
    val hasNext: Boolean? = null,

    @field:SerializedName("total_pages")
    val totalPages: String? = null,

    @field:SerializedName("current_page")
    val currentPage: String? = null
)