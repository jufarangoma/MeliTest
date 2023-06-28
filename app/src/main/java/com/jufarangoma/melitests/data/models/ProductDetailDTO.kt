package com.jufarangoma.melitests.data.models

import com.jufarangoma.melitests.domain.entities.Attribute as AttributesDomain
import com.google.gson.annotations.SerializedName
import com.jufarangoma.melitests.domain.entities.ProductDetail

data class ProductDetailDTO(
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("price")
    val price: Double? = null,
    @SerializedName("permalink")
    val permalink: String? = null,
    @SerializedName("pictures")
    val pictures: List<Pictures> = arrayListOf(),
    @SerializedName("shipping")
    val shipping: Shipping? = Shipping(),
    @SerializedName("attributes")
    val attributes: List<Attributes> = arrayListOf(),
    @SerializedName("warranty")
    val warranty: String? = null

) {
    fun mapToEntity() = ProductDetail(
        title = title,
        price = price,
        permalink = permalink,
        pictures = pictures.mapNotNull { it.secureUrl },
        freeShipping = shipping?.freeShipping ?: false,
        attributes = attributes.map { it.mapToDomain() },
        warranty = warranty
    )
}

data class Pictures(
    @SerializedName("secure_url")
    val secureUrl: String? = null
)

data class Shipping(
    @SerializedName("free_shipping")
    val freeShipping: Boolean? = null

)

data class Attributes(
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("value_name")
    val valueName: String? = null

) {
    fun mapToDomain() = AttributesDomain(
        name = name,
        valueName = valueName
    )
}
