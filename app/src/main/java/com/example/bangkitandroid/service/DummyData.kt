package com.example.bangkitandroid.service

import com.example.bangkitandroid.domain.entities.User

class DummyData {
    fun getUser(id: Int): User {
        return User(
            id = id,
            name = "user $id",
            imgUrl = "https://cdn.britannica.com/89/126689-004-D622CD2F/Potato-leaf-blight.jpg",
            phoneNumber = "123456789",
            password = "password"
        )
    }
}