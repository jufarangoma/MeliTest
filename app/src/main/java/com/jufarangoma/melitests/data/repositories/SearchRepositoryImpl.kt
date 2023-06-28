package com.jufarangoma.melitests.data.repositories

import com.jufarangoma.melitests.data.api.SearchApi
import com.jufarangoma.melitests.domain.repositories.SearchRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class SearchRepositoryImpl(
    private val searchApi: SearchApi
) : SearchRepository {

    override fun search(query: String) = flow {
        val result = searchApi.search(query)
        val listProducts = result.results.map { it.mapToDomainEntity() }
        emit(Result.success(listProducts))
    }.catch { throwable ->
        // If I had documentation about network exceptions I would add a mapper class to manage it
        emit(Result.failure(throwable))
    }
}
