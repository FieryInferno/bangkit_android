package com.example.bangkitandroid.service

import com.example.bangkitandroid.data.remote.response.LoginResponse
import com.example.bangkitandroid.data.remote.response.RegisterResponse
import com.example.bangkitandroid.data.remote.response.EditProfileResponse
import com.example.bangkitandroid.domain.entities.*

class DummyData {

    // For Testing Purpose
    private fun getProductsRecommendation(): List<Product> {
        val products = ArrayList<Product>()
        for(i in 1..5){
            val product = Product(
                id = i,
                title = "Obat Tanaman $i",
                imgUrl = "https://cdn.britannica.com/89/126689-004-D622CD2F/Potato-leaf-blight.jpg",
                price = 10000,
                createdAt = "",
                updatedAt = "",
                description = "",
                url = ""
            )
            products.add(product)
        }
        return products
    }

    fun getDetailDisease(id: Int): Disease {
        return Disease(
            id = id,
            title = "Nama Penyakit",
            description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            treatment = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, .",
            createdAt = "4 Mei 2023 9:00",
            updatedAt = "4 Mei 2023 9:00",
            products = getProductsRecommendation(),
        )
    }

    fun getHistoryDiseases(): List<Disease> {
        val diseases = ArrayList<Disease>()
        for(i in 1..5){
            val disease = getDetailDisease(i)
            diseases.add(disease)
        }
        return diseases
    }

    fun getUser(id: Int): User {
        return User(
            id = id,
            name = "user $id",
            imgUrl = "https://cdn.britannica.com/89/126689-004-D622CD2F/Potato-leaf-blight.jpg",
            phoneNumber = "123456789",
            password = "password"
        )
    }

    private fun getComment(): List<Comment> {
        val comments = ArrayList<Comment>()
        for(i in 1..5){
            val comment = Comment(
                id = i,
                user = getUser(i),
                description = "Comment $i",
                dateTime = "4 Mei 2023 9:00"
            )
            comments.add(comment)
        }
        return comments
    }
    fun getDetailBlog(id: Int): Blog {
        return Blog(
            id = id,
            title = "Judul Blog $id",
            imgUrl = "https://cdn.britannica.com/89/126689-004-D622CD2F/Potato-leaf-blight.jpg",
            description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            dateTime = "4 Mei 2023 9:00",
            user = id,
        )
    }

    fun getListBlogs(): List<Blog> {
        val blogs = ArrayList<Blog>()
        for(i in 1..10){
            val blog = getDetailBlog(i)
            blogs.add(blog)
        }
        return blogs
    }

    // For supply placeholder purpose
    fun getHistoryDiseasesDummy(): List<Disease> {
        val diseases = ArrayList<Disease>()
        for(i in 1..5){
            val disease = getDetailDiseaseDummy(i)
            diseases.add(disease)
        }
        return diseases
    }

    fun getDetailDiseaseDummy(id: Int): Disease {
        return Disease(
            id = id,
            title = "Penyakit $id",
            description = "Description $id",
            treatment = "Treatment $id",
            createdAt = "21 Mei 2023 22:00",
            updatedAt = "21 Mei 2023 22:00",
            products = getProductsRecommendation(),
        )
    }

    fun getListBlogsDummy(): List<Blog> {
        val blogs = ArrayList<Blog>()
        for(i in 1..5){
            val blog = getDetailBlogDummy(i)
            blogs.add(blog)
        }
        return blogs
    }

    fun getDetailBlogDummy(id: Int): Blog {
        return Blog(
            id = id,
            title = "Judul Blog $id",
            imgUrl = "https://cdn.britannica.com/89/126689-004-D622CD2F/Potato-leaf-blight.jpg",
            description = "Description $id",
            dateTime = "21 Mei 2023 22:00",
            user = id,
        )
    }

    private fun getCommentDummy(): List<Comment> {
        val comments = ArrayList<Comment>()
        for(i in 1..5){
            val comment = Comment(
                id = i,
                user = getUser(i),
                description = "Comment $i",
                dateTime = "21 Mei 2023 22:00"
            )
            comments.add(comment)
        }
        return comments
    }

    fun generateEditProfileResponse(): EditProfileResponse {
        return EditProfileResponse(
            success = true,
            message = "success",
            data = getUser(1)
        )
    }

    fun generateLoginResponse(): LoginResponse {
        return LoginResponse(
            success = true,
            message = "success",
            data = getUser(1)
        )
    }

    fun generateRegisterResponse(): RegisterResponse {
        return RegisterResponse(
            success = true,
            message = "success",
            data = getUser(1)
        )
    }

    // For supply placeholder purpose
    fun getUserDummy(id: Int): User {
        return User(
            id = id,
            name = "Name $id",
            imgUrl = "https://agrisustineri.org/wp-content/uploads/2022/08/The-Story-of-Todays-Successful-Young-Farmers-.jpg",
            phoneNumber = "0123456789",
            password = "Password $id",
        )
    }
}