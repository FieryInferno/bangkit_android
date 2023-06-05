//package com.example.bangkitandroid.ui.home
//
//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import com.example.bangkitandroid.data.remote.Repository
//import com.example.bangkitandroid.data.remote.retrofit.ApiService
//import com.example.bangkitandroid.domain.entities.Blog
//import com.example.bangkitandroid.domain.entities.Disease
//import com.example.bangkitandroid.service.DummyData
//import com.example.bangkitandroid.service.Result
//import com.example.bangkitandroid.data.remote.FakeApiService
//import com.example.bangkitandroid.utils.MainDispatcherRule
//import com.example.bangkitandroid.utils.observeForTesting
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.test.runTest
//import org.junit.Assert.*
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//
//@ExperimentalCoroutinesApi
//class HomeViewModelTest {
//    @get:Rule
//    val instantExecutorRule = InstantTaskExecutorRule()
//
//    @get:Rule
//    val mainDispatcherRule = MainDispatcherRule()
//
//    private lateinit var apiService: ApiService
//    private lateinit var repository: Repository
//
//    @Before
//    fun setUp() {
//        apiService = FakeApiService()
//        repository = Repository(apiService)
//    }
//
//    @Test
//    fun `when getHistory Should Not Null and Return Data`() = runTest {
//        val dummyToken = "Bearer 123456"
//        val expectedHistory = DummyData().getHistoryDiseases()
//
//        val homeViewModel = HomeViewModel(repository)
//        val actualHistory = homeViewModel.getHistory(dummyToken)
//
//        actualHistory.observeForTesting {
//            assertNotNull(actualHistory)
//            assertEquals(expectedHistory, (actualHistory.value as Result.Success).data)
//        }
//    }
//
//    @Test
//    fun `when getHistory Empty Should Return No Data`() = runTest {
//        val dummyToken = "Bearer 123456"
//        val expectedHistory = emptyList<Disease>()
//
//        val homeViewModel = HomeViewModel(repository)
//        val actualHistory = homeViewModel.getHistory(dummyToken)
//
//        actualHistory.observeForTesting {
//            assertEquals(0, (actualHistory.value as Result.Success).data.size)
//            assertEquals(expectedHistory, (actualHistory.value as Result.Success).data)
//        }
//    }
//
//    @Test
//    fun `when getBlog Should Not Null and Return Data`() = runTest {
//        val expectedBlogs = DummyData().getListBlogs()
//
//        val homeViewModel = HomeViewModel(repository)
//        val actualBlogs = homeViewModel.getBlog()
//
//        actualBlogs.observeForTesting {
//            assertNotNull(actualBlogs)
//            assertEquals(expectedBlogs, (actualBlogs.value as Result.Success).data)
//        }
//    }
//
//    @Test
//    fun `when getBlog Empty Should Return No Data`() = runTest {
//        val expectedBlogs = emptyList<Blog>()
//
//        val homeViewModel = HomeViewModel(repository)
//        val actualBlogs = homeViewModel.getBlog()
//
//        actualBlogs.observeForTesting {
//            assertEquals(0, (actualBlogs.value as Result.Success).data.size)
//            assertEquals(expectedBlogs, (actualBlogs.value as Result.Success).data)
//        }
//    }
//}