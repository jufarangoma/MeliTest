package com.jufarangoma.melitests.domain.entities

data class ProductDetail(
    val title: String? = null,
    val price: Double? = null,
    val permalink: String? = null,
    val pictures: List<String> = arrayListOf(),
    val freeShipping: Boolean = false,
    val attributes: List<Attribute> = arrayListOf(),
    val warranty: String? = null
)

data class Attribute(
    val name: String? = null,
    val valueName: String? = null
)
