package com.example.bangkitandroid.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.bangkitandroid.data.remote.model.BlogModel
import com.example.bangkitandroid.data.remote.retrofit.ApiService
import com.example.bangkitandroid.domain.entities.Blog
import com.example.bangkitandroid.domain.mapper.toListBlog

class BlogPagingSource(
    private val apiService: ApiService,
): PagingSource<Int, Blog>() {
    override fun getRefreshKey(state: PagingState<Int, Blog>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Blog> {
        return try {
            val page = params.key ?: INITIAL_PAGE_INDEX
            val response = apiService.getListBlogs(page, 10)
            LoadResult.Page(
                data = response.toListBlog(),
                prevKey = if (response.meta.hasPrevious) page - 1 else null,
                nextKey = if (response.meta.hasNext) page + 1 else null,
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}