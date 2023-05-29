package com.example.bangkitandroid.ui.home

import androidx.lifecycle.ViewModel

class HomeViewModel(private val repository: Repository): ViewModel() {
    fun getHistory(token: String) = repository.getHistory(token)

    fun getBlog() = repository.getListBlog()
}