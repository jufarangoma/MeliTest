package com.jufarangoma.melitests.presentation

import androidx.lifecycle.MutableLiveData
import com.jufarangoma.melitests.domain.entities.ProductEntity
import com.jufarangoma.melitests.domain.exceptions.DomainException
import com.jufarangoma.melitests.domain.repositories.SearchRepository
import com.jufarangoma.melitests.presentation.viewmodels.SearchViewModel
import com.jufarangoma.melitests.utils.MainCoroutineRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyOrder
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SearchViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val searchRepository = mockk<SearchRepository>(relaxed = true)
    private val arrayListOfProducts = mockk<ArrayList<ProductEntity>>(relaxed = true)
    private val mutableLiveData = mockk<MutableLiveData<RequestState>>(relaxed = true)
    private lateinit var searchViewModel: SearchViewModel

    @Before
    fun setUp() {
        searchViewModel = SearchViewModel(
            searchRepository = searchRepository,
            coroutineDispatcher = mainCoroutineRule.testDispatcher,
            arrayListOfProducts = arrayListOfProducts,
            mutableLiveData = mutableLiveData
        )
    }

    @After
    fun tearDown() {
        confirmVerified(
            searchRepository,
            arrayListOfProducts,
            mutableLiveData
        )
    }

    @Test
    fun searchViewModelSuccess() = runTest(mainCoroutineRule.testDispatcher) {
        val productEntity = mockk<ProductEntity>()

        coEvery { searchRepository.search("Motorola") } returns flowOf(
            Result.success(listOf(productEntity))
        )

        searchViewModel.search("Motorola")

        coVerify {
            searchRepository.search("Motorola")
        }

        verifyOrder {
            mutableLiveData.postValue(RequestState.Loading)
            arrayListOfProducts.clear()
            arrayListOfProducts.addAll(listOf(productEntity))
            mutableLiveData.postValue(RequestState.Success)
        }

        confirmVerified(productEntity)
    }

    @Test
    fun searchViewModelFailure() = runTest(mainCoroutineRule.testDispatcher) {
        val domainException = DomainException()
        coEvery { searchRepository.search("Motorola") } returns flowOf(
            Result.failure(domainException)
        )

        searchViewModel.search("Motorola")

        coVerify {
            searchRepository.search("Motorola")
        }

        verifyOrder {
            mutableLiveData.postValue(RequestState.Loading)
            mutableLiveData.postValue(RequestState.Error)
        }
    }

    @Test
    fun clearState() {
        searchViewModel.clearStates()

        verify {
            mutableLiveData.postValue(RequestState.Empty)
        }
    }
}
