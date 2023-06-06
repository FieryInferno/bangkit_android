package com.example.bangkitandroid.ui.blog

import androidx.lifecycle.ViewModel
import com.example.bangkitandroid.data.remote.Repository

class BlogViewModel(private val repository: Repository) : ViewModel() {
    fun getListBlog() = repository.getListBlog()

    fun getBlog(id: Int) = repository.getBlogDetail(id)

//    fun getListComment() = repository.getListComment()
//
//    fun postComment(token: String, dateTime: String, description: String) =
//        repository.postComment(token, dateTime, description)
}