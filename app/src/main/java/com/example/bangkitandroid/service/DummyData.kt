package com.example.bangkitandroid.service

import com.example.bangkitandroid.data.remote.model.BlogModel
import com.example.bangkitandroid.data.remote.model.CommentModel
import com.example.bangkitandroid.data.remote.model.DiseaseModel
import com.example.bangkitandroid.data.remote.model.HistoryModel
import com.example.bangkitandroid.data.remote.model.ProductModel
import com.example.bangkitandroid.data.remote.model.UserModel
import com.example.bangkitandroid.data.remote.response.*
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

    private fun getProductsModelRecommendation(): List<ProductModel> {
        val products = ArrayList<ProductModel>()
        for(i in 1..5){
            val product = ProductModel(
                id = i,
                title = "Obat Tanaman $i",
                image = "https://cdn.britannica.com/89/126689-004-D622CD2F/Potato-leaf-blight.jpg",
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
            image = "image.jpg"
        )
    }

    fun getDetailDiseaseModel(id: Int): DiseaseModel {
        return DiseaseModel(
            id = id,
            name = "Nama Penyakit",
            description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            solution = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, .",
            createdAt = "4 Mei 2023 9:00",
            updatedAt = "4 Mei 2023 9:00",
            products = getProductsModelRecommendation(),
        )
    }

    fun getDiseaseResponse(id: Int): DiseaseResponse {
        return DiseaseResponse(
            success = true,
            statusCode = 200,
            data = HistoryModel(
                image = "image.jpg",
                disease = DummyData().getDetailDiseaseModel(0),
                id = 0,
                user = 0,
                timestamp = ""
            )
        )
    }
    fun getDiseaseResponses(): List<DiseaseResponse> {
        val diseases = ArrayList<DiseaseResponse>()
        for(i in 1..5){
            val disease = getDiseaseResponse(i)
            diseases.add(disease)
        }
        return diseases
    }


    fun getHistoryModels(): List<HistoryModel> {
        val diseases = ArrayList<HistoryModel>()
        for(i in 1..5){
            val disease = getHistoryModel(i)
            diseases.add(disease)
        }
        return diseases
    }

    fun getHistoryModel(id: Int): HistoryModel {
        return HistoryModel(
            image = "image.jpg",
            disease = DummyData().getDetailDiseaseModel(0),
            id = id,
            user = 0,
            timestamp = ""
        )
    }

    fun getUser(id: Int): User {
        return User(
            id = id,
            name = "user $id",
            imgUrl = "https://cdn.britannica.com/89/126689-004-D622CD2F/Potato-leaf-blight.jpg",
            phoneNumber = "123456789",
        )
    }

    fun getComment(): List<CommentModel> {
        val comments = ArrayList<CommentModel>()
        for(i in 1..5){
            val comment = CommentModel(
                blogId = 0,
                id = i,
                user = UserModel(
                    name = "Penulis",
                    phoneNumber = "081234567890",
                    email = null,
                    image = ""
                ),
                message = "Comment $i",
                timestamp = "4 Mei 2023 9:00"
            )
            comments.add(comment)
        }
        return comments
    }
    fun getDetailBlog(id: Int): Blog {
        return Blog(
            id = id,
            title = "Judul Blog $id",
            image = "https://cdn.britannica.com/89/126689-004-D622CD2F/Potato-leaf-blight.jpg",
            description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            timestamp = "4 Mei 2023 9:00",
            user = User(
                id = 0,
                name = "Penulis",
                phoneNumber = "081234567890",
                imgUrl = ""
            ),
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

    fun getBlogModel(id: Int): BlogModel {
        return BlogModel(
            image = "https://cdn.britannica.com/89/126689-004-D622CD2F/Potato-leaf-blight.jpg",
            description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            id = id,
            title = "Judul Blog $id",
            user = UserModel(
                name = "Penulis",
                phoneNumber = "081234567890",
                email = null,
                image = ""
            ),
            timestamp = "4 Mei 2023 9:00"
        )
    }

    fun getListBlogModels(): List<BlogModel> {
        val blogs = ArrayList<BlogModel>()
        for (i in 1..5) {
            val blog = getBlogModel(i)
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
            image = "image.jpg"
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
            image = "https://cdn.britannica.com/89/126689-004-D622CD2F/Potato-leaf-blight.jpg",
            description = "Description $id",
            timestamp = "21 Mei 2023 22:00",
            user = User(
                id = 0,
                name = "Penulis",
                phoneNumber = "081234567890",
                imgUrl = ""
            ),
        )
    }

    fun getCommentDummy(): List<Comment> {
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

    //    fun generateLoginResponse(): LoginResponse {
//        return LoginResponse(
//            success = true,
//            message = "success",
//            data = getUser(1)
//        )
//    }
//
//    fun generateRegisterResponse(): RegisterResponse {
//        return RegisterResponse(
//            success = true,
//            message = "success",
//            data = getUser(1)
//        )
//    }

    // For supply placeholder purpose
    fun getUserDummy(id: Int): User {
        return User(
            id = id,
            name = "Name $id",
            imgUrl = "https://agrisustineri.org/wp-content/uploads/2022/08/The-Story-of-Todays-Successful-Young-Farmers-.jpg",
            phoneNumber = "0123456789",
        )
    }

    fun getLoginResult(): LoginResult {
        return LoginResult(
            token="token",
            refreshToken="token",
            user=DataUser(
                name="",
                phoneNumber="",
                email=null)
        )
    }

    fun getRegisterResult(): RegisterResult {
        return RegisterResult(
            password="",
            isSuperuser=false,
            isActive=true, userPermissions= emptyList(),
            isStaff=false,
            lastLogin="",
            name="",
            groups= emptyList(),
            phoneNumber="",
            id=0,
            email="",
            image="")
    }
}