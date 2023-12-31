package com.jufarangoma.melitests.data

import com.jufarangoma.melitests.data.api.ProductDetailApi
import com.jufarangoma.melitests.data.models.ProductDetailDTO
import com.jufarangoma.melitests.data.repositories.ProductDetailRepositoryImpl
import com.jufarangoma.melitests.domain.entities.ProductDetail
import com.jufarangoma.melitests.domain.exceptions.DomainExceptionStrategy
import com.jufarangoma.melitests.domain.exceptions.UnknownException
import com.jufarangoma.melitests.domain.repositories.ProductDetailRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class ProductDetailRepositoryTest {

    private val productDetailApi = mockk<ProductDetailApi>()
    private val domainExceptionStrategy = mockk<DomainExceptionStrategy>()
    lateinit var productDetailRepository: ProductDetailRepository

    @Before
    fun setUp() {
        productDetailRepository = ProductDetailRepositoryImpl(
            productDetailApi,
            domainExceptionStrategy
        )
    }

    @After
    fun tearDown() {
        confirmVerified(
            productDetailApi,
            domainExceptionStrategy
        )
    }

    @Test
    fun productDetailSuccess() = runBlocking {
        val productDetailDTO = mockk<ProductDetailDTO>()
        val productDetail = mockk<ProductDetail>()

        every { productDetailDTO.mapToEntity() } returns productDetail
        coEvery { productDetailApi.getProductDetail("Motorola_5G") } returns productDetailDTO

        productDetailRepository.getProductDetail("Motorola_5G").collect {
            assert(it.isSuccess)
        }

        verify {
            productDetailDTO.mapToEntity()
        }
        coVerify {
            productDetailApi.getProductDetail("Motorola_5G")
        }

        confirmVerified(
            productDetailDTO,
            productDetail
        )
    }

    @Test
    fun productDetailFailure() = runBlocking {
        val throwable = Throwable("404 Error")
        every { domainExceptionStrategy.manageException(throwable) } returns UnknownException
        coEvery { productDetailApi.getProductDetail("Motorola_5G") } throws throwable

        productDetailRepository.getProductDetail("Motorola_5G").collect {
            assert(it.isFailure)
        }

        verify {
            domainExceptionStrategy.manageException(throwable)
        }
        coVerify {
            productDetailApi.getProductDetail("Motorola_5G")
        }
    }
}
