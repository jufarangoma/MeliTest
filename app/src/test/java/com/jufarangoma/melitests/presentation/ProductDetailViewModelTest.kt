package com.jufarangoma.melitests.presentation

import androidx.lifecycle.MutableLiveData
import com.jufarangoma.melitests.domain.entities.ProductDetail
import com.jufarangoma.melitests.domain.exceptions.DomainException
import com.jufarangoma.melitests.domain.exceptions.UnknownException
import com.jufarangoma.melitests.domain.repositories.ProductDetailRepository
import com.jufarangoma.melitests.presentation.viewmodels.ProductDetailViewModel
import com.jufarangoma.melitests.utils.MainCoroutineRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import io.mockk.verifyOrder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProductDetailViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val productDetailRepository = mockk<ProductDetailRepository>(relaxed = true)
    private val mutableLiveData = mockk<MutableLiveData<RequestState>>(relaxed = true)
    private lateinit var productDetailViewModel: ProductDetailViewModel

    @Before
    fun setUp() {
        productDetailViewModel = ProductDetailViewModel(
            productDetailRepository = productDetailRepository,
            coroutineDispatcher = mainCoroutineRule.testDispatcher,
            mutableLiveData = mutableLiveData
        )
    }

    @After
    fun tearDown() {
        confirmVerified(
            productDetailRepository,
            mutableLiveData
        )
    }

    @Test
    fun getProductDetailWithProductIdNull() = runTest(mainCoroutineRule.testDispatcher) {
        val slot = slot<RequestState.Error>()
        productDetailViewModel.productId = null

        productDetailViewModel.getProductDetail()

        verify {
            mutableLiveData.postValue(capture(slot))
        }

        assert(productDetailViewModel.productDetail == null)
        assert(slot.captured.exception is UnknownException)
    }

    @Test
    fun getProductDetailSuccess() = runTest(mainCoroutineRule.testDispatcher) {
        productDetailViewModel.productId = "MLA_12"
        val productDetail = mockk<ProductDetail>()

        coEvery {
            productDetailRepository.getProductDetail("MLA_12")
        } returns flowOf(Result.success(productDetail))

        productDetailViewModel.getProductDetail()

        coVerify {
            productDetailRepository.getProductDetail("MLA_12")
        }

        verifyOrder {
            mutableLiveData.postValue(RequestState.Loading)
            mutableLiveData.postValue(RequestState.Success)
        }
        assert(productDetailViewModel.productDetail != null)

        confirmVerified(productDetail)
    }

    @Test
    fun getProductDetailFailure() = runTest(mainCoroutineRule.testDispatcher) {
        val slot = slot<RequestState.Error>()

        productDetailViewModel.productId = "MLA_12"
        val domainException = DomainException()

        coEvery {
            productDetailRepository.getProductDetail("MLA_12")
        } returns flowOf(Result.failure(domainException))

        productDetailViewModel.getProductDetail()

        coVerify {
            productDetailRepository.getProductDetail("MLA_12")
        }

        verifyOrder {
            mutableLiveData.postValue(RequestState.Loading)
            mutableLiveData.postValue(capture(slot))
        }
        assert(productDetailViewModel.productDetail == null)
    }
}
