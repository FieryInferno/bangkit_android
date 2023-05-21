package com.example.bangkitandroid.ui.blog

import androidx.lifecycle.ViewModel
import com.example.bangkitandroid.data.remote.Repository

class BlogViewModel(private val repository: Repository) : ViewModel() {
    fun getListBlog() = repository.getListBlog()

    fun getBlog() = repository.getBlogDetail(0)
}