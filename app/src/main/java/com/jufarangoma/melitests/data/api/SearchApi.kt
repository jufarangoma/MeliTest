package com.jufarangoma.melitests.data.api

import com.jufarangoma.melitests.data.models.SearchDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {

    @GET("/sites/MLA/search")
    suspend fun search(@Query("q") query: String): SearchDTO
}
