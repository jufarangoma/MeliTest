package com.jufarangoma.melitests.data

import com.jufarangoma.melitests.data.models.Product
import org.junit.Test

class MapperSearchTest {

    @Test
    fun mapToDomainEntityEmpty() {
        val product = Product()

        val productEntity = product.mapToDomainEntity()

        assert(productEntity.id == String())
        assert(productEntity.name == String())
        assert(productEntity.image == String())
        assert(productEntity.price == 0.0)
        assert(productEntity.originalPrice == null)
        assert(productEntity.seller == null)
    }

    @Test
    fun mapToDomainEntityNotEmpty() {
        val product = Product(
            id = "Moto_G5",
            title = "Moto",
            permalink = "url",
            thumbnail = "image",
            price = 999.0,
            originalPrice = 1200.0,
            officialStoreName = "Motorola"
        )

        val productEntity = product.mapToDomainEntity()

        assert(productEntity.id == "Moto_G5")
        assert(productEntity.name == "Moto")
        assert(productEntity.image == "image")
        assert(productEntity.price == 999.0)
        assert(productEntity.originalPrice == 1200.0)
        assert(productEntity.seller == "Motorola")
    }
}