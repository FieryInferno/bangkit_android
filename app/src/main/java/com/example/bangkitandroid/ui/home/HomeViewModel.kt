package com.example.bangkitandroid.ui.home

import androidx.lifecycle.ViewModel
import com.example.bangkitandroid.data.remote.Repository

class HomeViewModel(private val repository: Repository): ViewModel() {
//    fun getHistory(token: String) = repository.getHistory(token)

    fun getBlog() = repository.getListBlog()
}