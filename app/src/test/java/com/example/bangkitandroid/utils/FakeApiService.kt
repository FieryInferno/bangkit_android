package com.example.bangkitandroid.utils

import com.example.bangkitandroid.data.remote.response.BlogResponse
import com.example.bangkitandroid.data.remote.response.ListBlogResponse
import com.example.bangkitandroid.data.remote.retrofit.ApiService
import com.example.bangkitandroid.service.DummyData

class FakeApiService : ApiService {
    override fun getBlog(id: Int): BlogResponse {
        return BlogResponse(
            status = true,
            message = "success",
            blog = DummyData().getDetailBlog(id)
        )
    }

    override fun getBlogs(page: Int, size: Int): ListBlogResponse {
        return ListBlogResponse(
            status = true,
            message = "success",
            blogs = DummyData().getListBlogs()
        )
    }
}
