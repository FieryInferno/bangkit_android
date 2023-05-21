package com.example.bangkitandroid.ui.profile

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.bangkitandroid.data.Repository
import com.example.bangkitandroid.data.remote.retrofit.ApiService
import com.example.bangkitandroid.domain.entities.User
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
class ProfileViewModelTest {
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
    fun `when getUser Should Not Null and Return Data`() = runTest {
        val expectedUser = DummyData().getUser(1)

        val profileViewModel = ProfileViewModel(repository)
        val actualUser = profileViewModel.getUser()

        actualUser.observeForTesting {
            assertNotNull(actualUser)
            assertEquals(expectedUser, (actualUser.value as Result.Success).data)
        }
    }

    @Test
    fun `when getUser Empty Should Return No Data`() = runTest {
        val expectedUser: User? = null

        val profileViewModel = ProfileViewModel(repository)
        val actualUser = profileViewModel.getUser()

        actualUser.observeForTesting {
            assertEquals(expectedUser, (actualUser.value as Result.Success).data)
        }
    }

    @Test
    fun `when editProfile Success Should Return Success`() = runTest {
        val expectedResponse = DummyData().getUser(1)

        val profileViewModel = ProfileViewModel(repository)
        val actualResponse = profileViewModel.editUser("dummy name", "000000")

        actualResponse.observeForTesting {
            assertNotNull(actualResponse)
            assertEquals(expectedResponse, (actualResponse.value as Result.Success).data)
        }
    }

    @Test
    fun `when editProfile Failed Should Return Failed`() = runTest {
        val expectedResponse = DummyData().getUser(1)

        val profileViewModel = ProfileViewModel(repository)
        val actualResponse = profileViewModel.editUser("dummy name", "000000")

        actualResponse.observeForTesting {
            assertEquals(expectedResponse, (actualResponse.value as Result.Success).data)
        }
    }
}