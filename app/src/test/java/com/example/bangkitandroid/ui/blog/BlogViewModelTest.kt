package com.example.bangkitandroid.ui.authentication

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.bangkitandroid.data.remote.Repository
import com.example.bangkitandroid.service.DummyData
import com.example.bangkitandroid.data.remote.FakeApiService
import com.example.bangkitandroid.ui.blog.BlogViewModel
import com.example.bangkitandroid.utils.MainDispatcherRule
import com.example.bangkitandroid.utils.observeForTesting
import kotlinx.coroutines.ExperimentalCoroutinesApi
import com.example.bangkitandroid.service.Result
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class BlogViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var apiService: FakeApiService
    private lateinit var repository: Repository

    @Mock
    private lateinit var mockRepository: Repository
    @Before
    fun setUp() {
        apiService = FakeApiService()
        repository = Repository(apiService, null)
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
    fun `when postComment Should Not Null and Return Correct Data`() = runTest {
        val expectedComment = DummyData().getComment()[0]

        val viewModel = BlogViewModel(repository)
        val actualComment = viewModel.postComment("message", 0)

        actualComment.observeForTesting {
            assertNotNull(actualComment)
            assertEquals(expectedComment, (actualComment.value as Result.Success).data)
        }
    }

    @Test
    fun `when postComment Should Return Error When Token Invalid`() = runTest {
        val expectedError = "invalid token"

        Mockito.`when`(mockRepository.postComment("message", 0))
            .thenReturn(MutableLiveData(Result.Error(error = "invalid token")))
        val viewModel = BlogViewModel(mockRepository)
        val actualComment = viewModel.postComment("message", 0)

        actualComment.observeForTesting {
            assertNotNull(actualComment)
            assertEquals(expectedError, (actualComment.value as Result.Error).error)
        }
    }
}
