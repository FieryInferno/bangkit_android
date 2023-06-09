package com.example.bangkitandroid.domain.mapper

import com.example.bangkitandroid.data.remote.model.BlogModel
import com.example.bangkitandroid.data.remote.response.BlogResponse
import com.example.bangkitandroid.data.remote.response.DiseaseHistoryResponse
import com.example.bangkitandroid.data.remote.response.ListBlogResponse
import com.example.bangkitandroid.domain.entities.Blog
import com.example.bangkitandroid.domain.entities.History
import com.example.bangkitandroid.domain.entities.User

fun ListBlogResponse.toListBlog() : List<Blog> = result.toListBlog()
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

fun BlogResponse.toBLog() : Blog = let {
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