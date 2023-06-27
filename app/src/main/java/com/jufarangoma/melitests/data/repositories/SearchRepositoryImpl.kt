package com.jufarangoma.melitests.data.repositories

import com.jufarangoma.melitests.data.api.SearchApi
import com.jufarangoma.melitests.domain.SearchRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class SearchRepositoryImpl(
    private val searchApi: SearchApi
) : SearchRepository {

    override fun search(query: String) =
        flow {
            val result = searchApi.search(query)
            emit(result)
        }.catch {
            println("+++ $it")
            emit(it)
        }
}
