//package com.example.bangkitandroid.data
//
//import android.util.Log
//import androidx.paging.PagingSource
//import androidx.paging.PagingState
//import com.example.bangkitandroid.data.remote.retrofit.ApiService
//import com.example.bangkitandroid.domain.entities.Comment
//import com.example.bangkitandroid.service.DummyData
//
//class CommentPagingSource(
//    private val apiService: ApiService,
//): PagingSource<Int, Comment>() {
//    override fun getRefreshKey(state: PagingState<Int, Comment>): Int? {
//        return state.anchorPosition?.let { anchorPosition ->
//            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
//                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
//        }
//    }
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Comment> {
//        return try {
//            val page = params.key ?: INITIAL_PAGE_INDEX
//            val response = DummyData().getDetailBlogDummy(0).comments
//            LoadResult.Page(
//                data = response,
//                prevKey = if (page == INITIAL_PAGE_INDEX) null else page - 1,
//                nextKey = if (response.isEmpty()) null else page + 1,
//            )
//        } catch (e: Exception) {
//            LoadResult.Error(e)
//        }
//    }
//
//    private companion object {
//        const val INITIAL_PAGE_INDEX = 1
//    }
//}