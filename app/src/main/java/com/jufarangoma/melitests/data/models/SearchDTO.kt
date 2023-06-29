package com.jufarangoma.melitests.data.models

import com.google.gson.annotations.SerializedName
import com.jufarangoma.melitests.domain.entities.ProductEntity

data class SearchDTO(
    @SerializedName("results")
    var results: List<Product>
)

data class Product(
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("permalink")
    var permalink: String? = null,
    @SerializedName("site_id")
    var siteId: String? = null,
    @SerializedName("category_id")
    var categoryId: String? = null,
    @SerializedName("domain_id")
    var domainId: String? = null,
    @SerializedName("thumbnail")
    var thumbnail: String? = null,
    @SerializedName("price")
    var price: Double? = null,
    @SerializedName("original_price")
    var originalPrice: Double? = null,
    @SerializedName("official_store_name")
    var officialStoreName: String? = null
) {
    fun mapToDomainEntity() = ProductEntity(
        id = id ?: String(),
        name = title ?: String(),
        image = thumbnail ?: String(),
        price = price ?: 0.0,
        originalPrice = originalPrice,
        seller = officialStoreName
    )
}
