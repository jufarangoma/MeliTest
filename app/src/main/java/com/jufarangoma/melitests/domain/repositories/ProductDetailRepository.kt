package com.jufarangoma.melitests.domain.repositories

import com.jufarangoma.melitests.domain.entities.ProductDetail
import kotlinx.coroutines.flow.Flow

interface ProductDetailRepository {

    fun getProductDetail(id: String): Flow<Result<ProductDetail>>
}
