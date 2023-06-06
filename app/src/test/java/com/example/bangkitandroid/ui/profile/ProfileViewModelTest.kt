package com.example.bangkitandroid.ui.profile

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.bangkitandroid.data.remote.Repository
import com.example.bangkitandroid.service.DummyData
import com.example.bangkitandroid.service.Result
import com.example.bangkitandroid.data.remote.FakeApiService
import com.example.bangkitandroid.utils.MainDispatcherRule
import com.example.bangkitandroid.utils.observeForTesting
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.RequestBody.Companion.toRequestBody
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
class ProfileViewModelTest {
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
    fun `when getUser Should Not Null and Return Data`() = runTest {
        val expectedUser = DummyData().getUser(0)

        val profileViewModel = ProfileViewModel(repository)
        val actualUser = profileViewModel.getUser()

        actualUser.observeForTesting {
            assertNotNull(actualUser)
            assertEquals(expectedUser, (actualUser.value as Result.Success).data)
        }
    }

    @Test
    fun `when getUser Should Return Error When Token Invalid`() = runTest {
        val expectedError = "invalid token"

        Mockito.`when`(mockRepository.getUser())
            .thenReturn(MutableLiveData(Result.Error(error = "invalid token")))
        val profileViewModel = ProfileViewModel(mockRepository)
        val actualResponse = profileViewModel.getUser()

        actualResponse.observeForTesting {
            assertNotNull(actualResponse)
            assertEquals(expectedError, (actualResponse.value as Result.Error).error)
        }
    }

    @Test
    fun `when editProfile Success Should Return Correct Data`() = runTest {
        val expectedResponse = DummyData().getUser(0)

        val profileViewModel = ProfileViewModel(repository)
        val actualResponse = profileViewModel.editProfile("user 0".toRequestBody(), "123456789".toRequestBody(), null)

        actualResponse.observeForTesting {
            assertNotNull(actualResponse)
            assertEquals(expectedResponse, (actualResponse.value as Result.Success).data)
        }
    }

//    @Test
//    fun `when editProfile Should Return Error When Token Invalid`() = runTest {
//        val expectedError = "invalid token"
//
//        Mockito.`when`(mockRepository.editProfile("user 0".toRequestBody(), "123456789".toRequestBody(), null))
//            .thenReturn(MutableLiveData(Result.Error(error = "invalid token")))
//        val profileViewModel = ProfileViewModel(mockRepository)
//        val actualResponse = profileViewModel.editProfile("user 0".toRequestBody(), "123456789".toRequestBody(), null)
//
//        print(actualResponse)
//
//        actualResponse.observeForTesting {
//            assertNotNull(actualResponse)
//            assertEquals(expectedError, (actualResponse.value as Result.Error).error)
//        }
//    }
}