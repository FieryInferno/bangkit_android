package com.example.bangkitandroid.data.remote.response

import com.example.bangkitandroid.data.remote.model.BlogModel
import com.example.bangkitandroid.data.remote.model.MetaModel
import com.example.bangkitandroid.domain.entities.Blog
import com.google.gson.annotations.SerializedName

data class ListBlogResponse (
    @field:SerializedName("result")
    val result: List<BlogModel>,

    @field:SerializedName("meta")
    val meta: MetaModel
)