package com.example.bangkitandroid.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.bangkitandroid.data.remote.Repository
import com.example.bangkitandroid.data.remote.retrofit.ApiService
import com.example.bangkitandroid.domain.entities.Blog
import com.example.bangkitandroid.domain.entities.Disease
import com.example.bangkitandroid.service.DummyData
import com.example.bangkitandroid.service.Result
import com.example.bangkitandroid.utils.FakeApiService
import com.example.bangkitandroid.utils.MainDispatcherRule
import com.example.bangkitandroid.utils.observeForTesting
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class RepositoryTest {
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
    fun `when getListBlog Should Not Null and Return Correct Data`() = runTest {
        val expectedListBlogs = DummyData().getListBlogs()
        val actualListBlogs = repository.getListBlog()

        actualListBlogs.observeForTesting {
            assertNotNull(actualListBlogs)
            assertEquals(expectedListBlogs, (actualListBlogs.value as Result.Success).data)
        }
    }

    @Test
    fun `when getListBlog Empty Should Return No Data`() = runTest {
        val expectedListBlogs = emptyList<Blog>()
        val actualListBlogs = repository.getListBlog()

        actualListBlogs.observeForTesting {
            assertEquals(0, (actualListBlogs.value as Result.Success).data.size)
            assertEquals(expectedListBlogs, (actualListBlogs.value as Result.Success).data)
        }
    }

    @Test
    fun `when getBlog Should Not Null and Return Correct Data`() = runTest {
        val expectedBlog = DummyData().getDetailBlog(0)
        val actualBlog = repository.getBlogDetail(0)

        actualBlog.observeForTesting {
            assertNotNull(actualBlog)
            assertEquals(expectedBlog, (actualBlog.value as Result.Success).data)
        }
    }

    @Test
    fun `when getBlog Empty Should Return Data`() = runTest {
        val expectedBlog : Blog? = null
        val actualBlog = repository.getBlogDetail(0)

        actualBlog.observeForTesting {
            assertNotNull(actualBlog)
            assertEquals(expectedBlog, (actualBlog.value as Result.Success).data)
        }
    }
}