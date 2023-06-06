package com.example.bangkitandroid.ui.disease

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.paging.*
import com.example.bangkitandroid.data.remote.FakeApiService
import com.example.bangkitandroid.data.remote.Repository
import com.example.bangkitandroid.domain.entities.History
import com.example.bangkitandroid.service.DummyData
import com.example.bangkitandroid.service.Result
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
import org.mockito.Mockito.`when`
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
    fun `when postAnalyzeDisease Should Return Correct Data`() = runTest {
        val expectedDisease = DummyData().getDetailDisease(0)

        val viewModel = DiseaseViewModel(repository)
        viewModel.setFile(File(""))
        val actualDisease = viewModel.postAnalyzeDisease()

        actualDisease.observeForTesting {
            assertNotNull(actualDisease)
            assertEquals(expectedDisease, (actualDisease.value as Result.Success).data)
        }
    }

    @Test
    fun `when postAnalyzeDisease Should Return Error When Token Invalid`() = runTest {
        val expectedError = "invalid token"

        `when`(mockRepository.postAnalyzeDisease(File(""))).thenReturn(MutableLiveData(Result.Error(error = "invalid token")))
        val viewModel = DiseaseViewModel(mockRepository)
        viewModel.setFile(File(""))


        val actualDisease = viewModel.postAnalyzeDisease()

        actualDisease.observeForTesting {
            assertNotNull(actualDisease)
            assertEquals(expectedError, (actualDisease.value as Result.Error).error)
        }
    }

    @Test
    fun `when postAnalyzeDisease Should Return Error When File Empty`() = runTest {
        val viewModel = DiseaseViewModel(repository)

        val actualDisease = viewModel.postAnalyzeDisease()

        actualDisease.observeForTesting {
            assertNotNull(actualDisease)
            assert(actualDisease.value is Result.Error)
        }
    }



    @Test
    fun `when getHistoryDisease Should Return Correct Data`() = runTest {

        val viewModel = DiseaseViewModel(repository)
        val actual = viewModel.getHistoryDisease()


        actual.asLiveData().observeForever {
            assertNotNull(actual)
            assert(it is PagingData<History>)

        }
    }
}
