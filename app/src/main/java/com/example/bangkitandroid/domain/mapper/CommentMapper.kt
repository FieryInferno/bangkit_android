package com.example.bangkitandroid.domain.mapper

import com.example.bangkitandroid.data.remote.model.CommentModel
import com.example.bangkitandroid.domain.entities.Comment
import com.example.bangkitandroid.domain.entities.User

fun List<CommentModel>.toListComment() : List<Comment> = map {
    Comment(
        id = it.id,
        description = it.message,
        user = User(
            id = 0,
            name = it.user.name,
            phoneNumber = it.user.phoneNumber,
            imgUrl = it.user.image
        ),
        dateTime = it.timestamp,
    )
}