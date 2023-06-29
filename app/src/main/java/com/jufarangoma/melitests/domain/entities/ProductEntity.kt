package com.jufarangoma.melitests.domain.entities

import com.jufarangoma.melitests.utils.isNullOrEmpty
import kotlin.math.roundToInt

data class ProductEntity(
    val id: String,
    val image: String,
    val name: String,
    val price: Double,
    val originalPrice: Double?,
    val seller: String?,
    val fee: Double = price / 36
) {
    fun getDiscount(): Int {
        return if (originalPrice.isNullOrEmpty()) {
            0
        } else {
            100 - ((price / originalPrice!!) * 100).roundToInt()
        }
    }
}
