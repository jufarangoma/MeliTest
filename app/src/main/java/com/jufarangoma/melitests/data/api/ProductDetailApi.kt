package com.jufarangoma.melitests.data.api

import com.jufarangoma.melitests.data.models.ProductDetailDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductDetailApi {

    @GET("/items/{id}")
    suspend fun getProductDetail(@Path("id") id: String): ProductDetailDTO
}
