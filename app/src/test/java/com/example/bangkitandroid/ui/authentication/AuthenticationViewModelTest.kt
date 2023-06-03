//package com.example.bangkitandroid.ui.authentication
//
//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import com.example.bangkitandroid.data.remote.retrofit.ApiService
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
//class AuthenticationViewModelTest {
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
//    fun `when Login Should Return Correct Data`() = runTest {
//        val expectedLogin = DummyData().getUser(1)
//
//        val viewModel = AuthenticationViewModel(repository)
//        val actualLogin = viewModel.login("000000", "password")
//
//        actualLogin.observeForTesting {
//            assertNotNull(actualLogin)
//            assertEquals(expectedLogin,
//                (actualLogin.value as Result.Success).data)
//        }
//    }
//
//    @Test
//    fun `when Login Failed Should Return Failed`() = runTest {
//        val expectedResponse = DummyData().getUser(1)
//
//        val authViewModel = AuthenticationViewModel(repository)
//        val actualResponse = authViewModel.login("000000", "password")
//
//        actualResponse.observeForTesting {
//            assertEquals(expectedResponse, (actualResponse.value as Result.Success).data)
//        }
//    }
//
//    @Test
//    fun `when Register Success Should Return Success`() = runTest {
//        val expectedResponse = DummyData().getUser(1)
//
//        val authViewModel = AuthenticationViewModel(repository)
//        val actualResponse = authViewModel.register("dummy", "000000", "password")
//
//        actualResponse.observeForTesting {
//            assertNotNull(actualResponse)
//            assertEquals(expectedResponse, (actualResponse.value as Result.Success).data)
//        }
//    }
//
//    @Test
//    fun `when Register Failed Should Return Failed`() = runTest {
//        val expectedResponse = DummyData().getUser(1)
//
//        val authViewModel = AuthenticationViewModel(repository)
//        val actualResponse = authViewModel.register("dummy", "000000", "password")
//
//        actualResponse.observeForTesting {
//            assertEquals(expectedResponse, (actualResponse.value as Result.Success).data)
//        }
//    }
//}