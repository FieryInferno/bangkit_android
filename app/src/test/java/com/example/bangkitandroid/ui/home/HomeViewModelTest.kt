package com.example.bangkitandroid.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.bangkitandroid.data.remote.Repository
import com.example.bangkitandroid.service.DummyData
import com.example.bangkitandroid.service.Result
import com.example.bangkitandroid.data.remote.FakeApiService
import com.example.bangkitandroid.data.remote.response.HomeResponse
import com.example.bangkitandroid.domain.mapper.toListBlog
import com.example.bangkitandroid.domain.mapper.toListHistory
import com.example.bangkitandroid.utils.MainDispatcherRule
import com.example.bangkitandroid.utils.observeForTesting
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.io.File

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DiseaseViewModelTest {
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
        repository = Repository(apiService)
    }

    @Test
    fun `when getHome Should Return Correct Data`() = runTest {
        val viewModel = HomeViewModel(repository)
        val actual = viewModel.getHome()

        actual.observeForever {
            assertNotNull(actual)
            assertTrue(it is Result<HomeResponse>)
        }
    }

    @Test
    fun `when getHome Should Return Error When Token Invalid`() = runTest {
        val expectedError = "invalid token"

        Mockito.`when`(mockRepository.getHome())
            .thenReturn(MutableLiveData(Result.Error(error = "invalid token")))
        val homeViewModel = HomeViewModel(mockRepository)
        homeViewModel.setFile(File(""))

        val actualResponse = homeViewModel.getHome()

        actualResponse.observeForTesting {
            assertNotNull(actualResponse)
            assertEquals(expectedError, (actualResponse.value as Result.Error).error)
        }
    }

    @Test
    fun `when getHome History Should Not Null and Return Data`() = runTest {
        val expectedHistory = DummyData().getHistoryModels().toListHistory()

        val homeViewModel = HomeViewModel(repository)
        val actualHistory = homeViewModel.getHome()

        actualHistory.observeForTesting {
            assertNotNull(actualHistory)
            assertEquals(expectedHistory, (actualHistory.value as Result.Success).data.history.toListHistory())
        }
    }

    @Test
    fun `when getHome Blog Should Not Null and Return Data`() = runTest {
        val expectedBlogs= DummyData().getListBlogModels().toListBlog()

        val homeViewModel = HomeViewModel(repository)
        val actualBlogs = homeViewModel.getHome()

        actualBlogs.observeForTesting {
            assertNotNull(actualBlogs)
            assertEquals(expectedBlogs, (actualBlogs.value as Result.Success).data.blogs.toListBlog())
        }
    }
}