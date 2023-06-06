package com.example.bangkitandroid.domain.mapper

import com.example.bangkitandroid.data.remote.model.BlogModel
import com.example.bangkitandroid.domain.entities.Blog
import com.example.bangkitandroid.domain.entities.User

fun List<BlogModel>.toListBlog() : List<Blog> = map {
    Blog(
        id = it.id,
        title = it.title,
        description = it.description,
        image = it.image,
        user = User(
            id = 0,
            name = it.user.name,
            phoneNumber = it.user.phoneNumber,
            imgUrl = ""
        ),
        timestamp = it.timestamp,
    )
}