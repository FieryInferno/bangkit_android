package com.example.bangkitandroid.ui.disease

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.recyclerview.widget.ListUpdateCallback
import com.example.bangkitandroid.data.remote.FakeApiService
import com.example.bangkitandroid.data.remote.Repository
import com.example.bangkitandroid.service.Result
import com.example.bangkitandroid.domain.entities.Disease
import com.example.bangkitandroid.service.DummyData
import com.example.bangkitandroid.utils.AsyncPagingDataDiffer
import com.example.bangkitandroid.utils.MainDispatcherRule
import com.example.bangkitandroid.utils.observeForTesting
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.File

@ExperimentalCoroutinesApi
class DiseaseViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var apiService: FakeApiService
    private lateinit var repository: Repository

    @Before
    fun setUp() {
        apiService = FakeApiService()
        repository = Repository(apiService)
    }

    @Test
    fun `when postAnalyzeDisease Should Return Correct Data`() = runTest {
        val dummyToken = "Bearer 123456"
        val dummyFile = File(
            "pathname"
        )
        val expectedDisease = DummyData().getDetailDisease(0)

        val viewModel = DiseaseViewModel(repository)
        val actualDisease = viewModel.postAnalyzeDisease(dummyToken, dummyFile)

        actualDisease.observeForTesting {
            assertNotNull(actualDisease)
            assertEquals(expectedDisease,
                (actualDisease.value as Result.Success).data)
        }
    }

    @Test
    fun `when postAnalyzeDisease Should Return Error When Token Invalid`() = runTest {
        val emptyToken = ""
        val dummyFile = File(
            "pathname"
        )
        val expectedError = "invalid token"

        val viewModel = DiseaseViewModel(repository)
        val actualDisease = viewModel.postAnalyzeDisease(emptyToken, dummyFile)

        actualDisease.observeForTesting {
            assertNotNull(actualDisease)
            assertEquals(expectedError, (actualDisease.value as Result.Error).error)
        }
    }

//    @Test
//    fun `when getHistoryDisease Should Return Correct Data`() = runTest {
//        val dummyToken = "Bearer 123456"
//        val dummyData = DummyData().getHistoryDiseases()
//        val expected = DiseasePagingSource.snapshot(dummyData)
//
//        val viewModel = DiseaseViewModel(repository)
//        val actual = viewModel.getHistoryDisease(dummyToken)
//        actual.observeForTesting {
//            val differ = AsyncPagingDataDiffer(
//                diffCallback = DiseaseHistoryAdapter.DIFF_CALLBACK,
//                updateCallback = noopListUpdateCallback,
//                workerDispatcher = Dispatchers.Main,
//            )
//            val differ2 = AsyncPagingDataDiffer(
//                diffCallback = DiseaseHistoryAdapter.DIFF_CALLBACK,
//                updateCallback = noopListUpdateCallback,
//                workerDispatcher = Dispatchers.Main,
//            )
//            differ.submitData(expected)
//            actual.value?.let { differ2.submitData(it) }
//            assertEquals(differ.snapshot()[0], differ2.snapshot()[0])
//        }
//    }

}

class DiseasePagingSource : PagingSource<Int, Disease>() {
    companion object {
        fun snapshot(items: List<Disease>): PagingData<Disease> {
            return PagingData.from(items)
        }
    }
    override fun getRefreshKey(state: PagingState<Int, Disease>): Int {
        return 0
    }
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Disease> {
        return LoadResult.Page(emptyList(), 0, 1)
    }
}

val noopListUpdateCallback = object : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
}