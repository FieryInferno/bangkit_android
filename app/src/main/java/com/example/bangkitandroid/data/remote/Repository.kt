package com.example.bangkitandroid.data.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.paging.PagingData
import com.example.bangkitandroid.data.remote.retrofit.ApiService
import com.example.bangkitandroid.domain.entities.Blog
import com.example.bangkitandroid.domain.entities.Comment
import com.example.bangkitandroid.domain.entities.Disease
import com.example.bangkitandroid.service.DummyData
import com.example.bangkitandroid.service.Result

class Repository (
    private val apiService: ApiService,
) {
    private val listBlogsResult = MediatorLiveData<Result<List<Blog>>>()
    private val blogResult = MediatorLiveData<Result<Blog>>()
    private val commentResult = MediatorLiveData<Result<Comment>>()
    private lateinit var blog: Blog

    fun getBlogDetail(id: Int) : LiveData<Result<Blog>> {
        blogResult.value = Result.Success(DummyData().getDetailBlogDummy(id))
        blog = DummyData().getDetailBlogDummy(id)
        return blogResult
    }

    fun getListBlog() : LiveData<PagingData<Blog>> {
        val pagingDataResult = MediatorLiveData<PagingData<Blog>>()
        pagingDataResult.value = PagingData.from(DummyData().getListBlogsDummy())
        return pagingDataResult
    }

    fun getListComment() : LiveData<PagingData<Comment>> {
        val pagingDataResult = MediatorLiveData<PagingData<Comment>>()
        val listComment = blog.comments
        pagingDataResult.value = PagingData.from(listComment)
        return pagingDataResult
    }

    fun postComment(token: String, dateTime: String, description: String) : LiveData<Result<Comment>>{
        commentResult.value = Result.Success(blog.comments[0])
        return commentResult
    }

    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            apiService: ApiService,
        ): Repository = instance ?: synchronized(this){
            instance ?: Repository(apiService)
        }.also {
            instance = it
        }
    }
}