package com.jufarangoma.melitests.domain

import com.jufarangoma.melitests.domain.entities.ProductEntity
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun search(query: String): Flow<Result<List<ProductEntity>>>
}
