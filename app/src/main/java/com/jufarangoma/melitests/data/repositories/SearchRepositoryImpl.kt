package com.jufarangoma.melitests.data.repositories

import android.util.Log
import com.jufarangoma.melitests.data.api.SearchApi
import com.jufarangoma.melitests.domain.exceptions.DomainExceptionStrategy
import com.jufarangoma.melitests.domain.repositories.SearchRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class SearchRepositoryImpl(
    private val searchApi: SearchApi,
    private val exceptionStrategy: DomainExceptionStrategy
) : SearchRepository {

    override fun search(query: String) = flow {
        val result = searchApi.search(query)
        val listProducts = result.results.map { it.mapToDomainEntity() }
        emit(Result.success(listProducts))
    }.catch { throwable ->
        Log.e("SEARCH_ERROR", "search $query exception", throwable)
        emit(Result.failure(exceptionStrategy.manageException(throwable)))
    }
}
