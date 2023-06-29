package com.jufarangoma.melitests.data.repositories

import android.util.Log
import com.jufarangoma.melitests.data.api.ProductDetailApi
import com.jufarangoma.melitests.domain.repositories.DomainExceptionRepository
import com.jufarangoma.melitests.domain.repositories.ProductDetailRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class ProductDetailRepositoryImpl(
    private val productDetailApi: ProductDetailApi,
    private val exceptionRepository: DomainExceptionRepository
) : ProductDetailRepository {

    override fun getProductDetail(id: String) = flow {
        val result = productDetailApi.getProductDetail(id)
        emit(Result.success(result.mapToEntity()))
    }.catch { throwable ->
        Log.e("PRODUCT_DETAIL_ERROR", "Get product detail exception", throwable)
        emit(Result.failure(exceptionRepository.manageException(throwable)))
    }
}
