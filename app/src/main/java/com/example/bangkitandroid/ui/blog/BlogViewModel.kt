package com.example.bangkitandroid.ui.blog

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.bangkitandroid.data.remote.Repository
import com.example.bangkitandroid.data.remote.model.BlogModel
import com.example.bangkitandroid.data.remote.response.BlogResponse
import com.example.bangkitandroid.domain.entities.Blog
import com.example.bangkitandroid.domain.entities.Comment
import com.example.bangkitandroid.service.Result
import kotlinx.coroutines.flow.Flow

class BlogViewModel(private val repository: Repository) : ViewModel() {

    val listBlog: LiveData<PagingData<Blog>> =
        repository.getListBlogs().cachedIn(viewModelScope)
    fun getBlog(id: Int): LiveData<Result<Blog>> {
        return repository.getBlogDetail(id)
    }

    fun getSessionId() = repository.getSessionId()

    fun listComment(id_blog: Int): LiveData<PagingData<Comment>> =
        repository.getListComment(id_blog).cachedIn(viewModelScope)

    fun postComment(message: String, id_blog: Int) =
        repository.postComment(message, id_blog)
}