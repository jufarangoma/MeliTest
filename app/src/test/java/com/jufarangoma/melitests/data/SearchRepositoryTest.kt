package com.jufarangoma.melitests.data

import com.jufarangoma.melitests.data.api.SearchApi
import com.jufarangoma.melitests.data.models.Product
import com.jufarangoma.melitests.data.models.SearchDTO
import com.jufarangoma.melitests.data.repositories.SearchRepositoryImpl
import com.jufarangoma.melitests.domain.entities.ProductEntity
import com.jufarangoma.melitests.domain.exceptions.DomainExceptionStrategy
import com.jufarangoma.melitests.domain.exceptions.UnknownException
import com.jufarangoma.melitests.domain.repositories.SearchRepository
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

class SearchRepositoryTest {

    private val searchApi = mockk<SearchApi>()
    private val domainExceptionStrategy = mockk<DomainExceptionStrategy>()
    private lateinit var searchRepository: SearchRepository

    @Before
    fun setUp() {
        searchRepository = SearchRepositoryImpl(
            searchApi,
            domainExceptionStrategy
        )
    }

    @After
    fun tearDown() {
        confirmVerified(
            searchApi,
            domainExceptionStrategy
        )
    }

    @Test
    fun searchProductSuccess() = runBlocking {
        val searchDTO = mockk<SearchDTO>()
        val productEntity = mockk<ProductEntity>()
        val productDTO = mockk<Product>()

        every { searchDTO.results } returns listOf(productDTO)
        every { productDTO.mapToDomainEntity() } returns productEntity
        coEvery { searchApi.search("Motorola") } returns searchDTO

        searchRepository.search("Motorola").collect {
            assert(it.isSuccess)
        }

        verify {
            searchDTO.results
            productDTO.mapToDomainEntity()
        }
        coVerify {
            searchApi.search("Motorola")
        }

        confirmVerified(
            searchDTO,
            productEntity,
            productDTO
        )
    }

    @Test
    fun searchProductException() = runBlocking {
        val throwable = Throwable("404 Error")
        every { domainExceptionStrategy.manageException(throwable) } returns UnknownException
        coEvery { searchApi.search("Motorola") } throws throwable

        searchRepository.search("Motorola").collect {
            assert(it.isFailure)
        }

        verify {
            domainExceptionStrategy.manageException(throwable)
        }
        coVerify {
            searchApi.search("Motorola")
        }
    }
}
