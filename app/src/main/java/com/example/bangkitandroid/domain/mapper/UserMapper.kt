package com.example.bangkitandroid.domain.mapper

import com.example.bangkitandroid.data.remote.model.UserModel
import com.example.bangkitandroid.domain.entities.User

fun UserModel.toUser(): User = let {
    User(
        id = 0,
        name = it.name,
        imgUrl = it.image ?: "",
        phoneNumber = it.phoneNumber
    )
}