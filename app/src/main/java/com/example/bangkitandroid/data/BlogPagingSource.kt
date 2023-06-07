package com.example.bangkitandroid.data

import android.util.Log
import androidx.datastore.preferences.protobuf.Api
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.bangkitandroid.data.remote.model.BlogModel
import com.example.bangkitandroid.data.remote.response.BlogResponse
import com.example.bangkitandroid.data.remote.retrofit.ApiService

class BlogPagingSource(
    private val apiService: ApiService,
): PagingSource<Int, BlogModel>() {
    override fun getRefreshKey(state: PagingState<Int, BlogModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BlogModel> {
        return try {
            val page = params.key ?: INITIAL_PAGE_INDEX
            val response = apiService.getListBlogs(page, params.loadSize)
            Log.e("response", response.result.toString())
            LoadResult.Page(
                data = response.result,
                prevKey = if (page == INITIAL_PAGE_INDEX) null else page - 1,
                nextKey = if (response.result.isEmpty()) null else page + 1,
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}