package com.example.bangkitandroid.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.bangkitandroid.data.remote.retrofit.ApiService
import com.example.bangkitandroid.domain.entities.History
import com.example.bangkitandroid.domain.mapper.toListDisease

class DiseasePagingSource(private val apiService: ApiService, private val token: String) : PagingSource<Int, History>(){
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, History> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getDiseases(token, position, 10)

            LoadResult.Page(
                data = responseData.toListDisease(),
                prevKey = if (!responseData.meta.hasPrevious) null else position - 1,
                nextKey = if (!responseData.meta.hasNext) null else position + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, History>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}