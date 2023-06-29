package com.jufarangoma.melitests.domain

import com.jufarangoma.melitests.domain.entities.ProductEntity
import org.junit.Test

class ProductEntityTest {

    @Test
    fun testDiscountWithOriginalPriceNull() {
        val product = ProductEntity(
            id = "",
            image = "",
            name = "",
            price = 100.0,
            originalPrice = null,
            seller = ""
        )

        assert(product.getDiscount() == 0)
    }

    @Test
    fun testDiscount() {
        val product = ProductEntity(
            id = "",
            image = "",
            name = "",
            price = 80.0,
            originalPrice = 100.0,
            seller = ""
        )

        assert(product.getDiscount() == 20)
    }

    @Test
    fun testFee() {
        val product = ProductEntity(
            id = "",
            image = "",
            name = "",
            price = 72.0,
            originalPrice = 100.0,
            seller = ""
        )

        assert(product.fee == 2.0)
    }
}
