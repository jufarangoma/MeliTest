package com.jufarangoma.melitests.domain

import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun search(query: String): Flow<Any>
}
