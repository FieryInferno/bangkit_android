package com.example.bangkitandroid.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.bangkitandroid.data.remote.retrofit.ApiService
import com.example.bangkitandroid.domain.entities.Comment
import com.example.bangkitandroid.domain.mapper.toListComment
import com.example.bangkitandroid.service.DummyData

class CommentPagingSource(
    private val apiService: ApiService, private val id: Int
): PagingSource<Int, Comment>() {
    override fun getRefreshKey(state: PagingState<Int, Comment>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Comment> {
        return try {
            val page = params.key ?: INITIAL_PAGE_INDEX
            val response = apiService.getComment(id, page, 5)
            if (response.result.isEmpty()) {
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            } else {
                LoadResult.Page(
                    data = response.result.toListComment(),
                    prevKey = if (response.meta.hasPrevious) page - 1 else null,
                    nextKey = if (response.meta.hasNext) page + 1 else null,
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}