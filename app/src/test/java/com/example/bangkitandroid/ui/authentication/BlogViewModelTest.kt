package com.example.bangkitandroid.ui.authentication

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.recyclerview.widget.ListUpdateCallback
import com.example.bangkitandroid.data.remote.Repository
import com.example.bangkitandroid.data.remote.retrofit.ApiService
import com.example.bangkitandroid.domain.entities.Blog
import com.example.bangkitandroid.service.DummyData
import com.example.bangkitandroid.service.Result
import com.example.bangkitandroid.ui.blog.BlogViewModel
import com.example.bangkitandroid.data.remote.FakeApiService
import com.example.bangkitandroid.domain.entities.Comment
import com.example.bangkitandroid.ui.blog.BlogAdapter
import com.example.bangkitandroid.ui.blog.CommentAdapter
import com.example.bangkitandroid.ui.disease.DiseaseViewModel
import com.example.bangkitandroid.utils.MainDispatcherRule
import com.example.bangkitandroid.utils.observeForTesting
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class BlogViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var apiService: ApiService
    private lateinit var repository: Repository

    @Before
    fun setUp() {
        apiService = FakeApiService()
        repository = Repository(apiService)
    }

    @Test
    fun `when getListBlog Should Return Correct Data`() = runTest {
        val dummyData = DummyData().getListBlogs()
        val expected = BlogPagingSource.snapshot(dummyData)

        val viewModel = BlogViewModel(repository)
        val actual = viewModel.getListBlog()
        actual.observeForTesting {
            val differ = AsyncPagingDataDiffer(
                diffCallback = BlogAdapter.DIFF_CALLBACK,
                updateCallback = noopListUpdateCallback,
                workerDispatcher = Dispatchers.Main,
            )
            val differ2 = AsyncPagingDataDiffer(
                diffCallback = BlogAdapter.DIFF_CALLBACK,
                updateCallback = noopListUpdateCallback,
                workerDispatcher = Dispatchers.Main,
            )
            differ.submitData(expected)
            actual.value?.let { differ2.submitData(it) }
            assertEquals(differ.snapshot()[0], differ2.snapshot()[0])
        }
    }

    @Test
    fun `when getBlog Should Not Null and Return Correct Data`() = runTest {
        val expectedBlog = DummyData().getDetailBlog(0)

        val blogViewModel = BlogViewModel(repository)
        val actualBlog = blogViewModel.getBlog(0)

        actualBlog.observeForTesting {
            assertNotNull(actualBlog)
            assertEquals(expectedBlog, (actualBlog.value as Result.Success).data)
        }
    }

    @Test
    fun `when getBlog Empty Should Return Data`() = runTest {
        val expectedBlog : Blog? = null

        val blogViewModel = BlogViewModel(repository)
        val actualBlog = blogViewModel.getBlog(0)

        actualBlog.observeForTesting {
            assertNotNull(actualBlog)
            assertEquals(expectedBlog, (actualBlog.value as Result.Success).data)
        }
    }

    @Test
    fun `when postComment Should Return Correct Data`() = runTest {
        val dummyToken = "Bearer 123456"
        val expectedComment = DummyData().getDetailBlog(0).comments[0]

        val viewModel = BlogViewModel(repository)
        val actualComment = viewModel.postComment(dummyToken, "10/10/2010", "description comment")

        actualComment.observeForTesting {
            assertNotNull(actualComment)
            assertEquals(expectedComment,
                (actualComment.value as Result.Success).data)
        }
    }

    @Test
    fun `when postComment Should Return Error When Token Invalid`() = runTest {
        val emptyToken = ""
        val expectedError = "invalid token"

        val viewModel = BlogViewModel(repository)
        val actualComment = viewModel.postComment(emptyToken, "10/10/2010", "description comment")

        actualComment.observeForTesting {
            assertNotNull(actualComment)
            assertEquals(expectedError, (actualComment.value as Result.Error).error)
        }
    }

    @Test
    fun `when getComment Should Return Correct Data`() = runTest {
        val dummyData = DummyData().getDetailBlog(0).comments
        val expected = CommentPagingSource.snapshot(dummyData)

        val viewModel = BlogViewModel(repository)
        val actual = viewModel.getListComment()
        actual.observeForTesting {
            val differ = AsyncPagingDataDiffer(
                diffCallback = CommentAdapter.DIFF_CALLBACK,
                updateCallback = noopListUpdateCallback,
                workerDispatcher = Dispatchers.Main,
            )
            val differ2 = AsyncPagingDataDiffer(
                diffCallback = CommentAdapter.DIFF_CALLBACK,
                updateCallback = noopListUpdateCallback,
                workerDispatcher = Dispatchers.Main,
            )
            differ.submitData(expected)
            actual.value?.let { differ2.submitData(it) }
            assertEquals(differ.snapshot()[0], differ2.snapshot()[0])
        }
    }
}

class BlogPagingSource : PagingSource<Int, LiveData<List<Blog>>>() {
    companion object {
        fun snapshot(items: List<Blog>): PagingData<Blog> {
            return PagingData.from(items)
        }
    }
    override fun getRefreshKey(state: PagingState<Int, LiveData<List<Blog>>>): Int {
        return 0
    }
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LiveData<List<Blog>>> {
        return LoadResult.Page(emptyList(), 0, 1)
    }
}

class CommentPagingSource : PagingSource<Int, LiveData<List<Comment>>>() {
    companion object {
        fun snapshot(items: List<Comment>): PagingData<Comment> {
            return PagingData.from(items)
        }
    }
    override fun getRefreshKey(state: PagingState<Int, LiveData<List<Comment>>>): Int {
        return 0
    }
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LiveData<List<Comment>>> {
        return LoadResult.Page(emptyList(), 0, 1)
    }
}

val noopListUpdateCallback = object : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
}