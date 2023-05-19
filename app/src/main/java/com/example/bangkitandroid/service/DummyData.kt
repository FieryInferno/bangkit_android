package com.example.bangkitandroid.service
import com.example.bangkitandroid.domain.entities.*
class DummyData {

    private fun getProductsRecommendation(): List<Product> {
        val products = ArrayList<Product>()
        for(i in 1..5){
            val product = Product(
                id = i,
                title = "Obat Tanaman $i",
                imgUrl = "https://cdn.britannica.com/89/126689-004-D622CD2F/Potato-leaf-blight.jpg",
                price = "Rp ${i}0.000"
            )
            products.add(product)
        }
        return products
    }

    fun getDetailDisease(id: Int): Disease {
        return Disease(
            id = id,
            title = "Nama Penyakit",
            imgUrl = "https://cdn.britannica.com/89/126689-004-D622CD2F/Potato-leaf-blight.jpg",
            description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            treatment = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, .",
            dateTime = "4 Mei 2023 9:00",
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

    private fun getUser(id: Int): User {
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
            author = "author $id",
            comments = getComment(),
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
}