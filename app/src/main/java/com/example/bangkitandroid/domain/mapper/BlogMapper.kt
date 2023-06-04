package com.example.bangkitandroid.domain.mapper

import com.example.bangkitandroid.data.remote.model.BlogModel
import com.example.bangkitandroid.domain.entities.Blog

fun List<BlogModel>.toListBlog() : List<Blog> = map {
    Blog(
        id = it.id,
        title = it.title,
        description = it.description,
        image = it.image,
        user = it.user,
        timestamp = it.timestamp,
    )
}