package com.example.bangkitandroid.ui.blog

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.bangkitandroid.data.remote.Repository
import com.example.bangkitandroid.data.remote.response.BlogResponse
import com.example.bangkitandroid.domain.entities.Comment
import com.example.bangkitandroid.service.Result
import kotlinx.coroutines.flow.Flow

class BlogViewModel(private val repository: Repository) : ViewModel() {

    val listBlog: LiveData<PagingData<BlogResponse>> =
        repository.getListBlogs().cachedIn(viewModelScope)
    fun getBlog(id: Int): LiveData<Result<BlogResponse>> {
        Log.e("ViewModel", "Masuk")
        return repository.getBlogDetail(id)
    }

    val listComment: LiveData<PagingData<Comment>> =
        repository.getListComment().cachedIn(viewModelScope)

    fun postComment(token: String, dateTime: String, description: String) =
        repository.postComment(token, dateTime, description)
}