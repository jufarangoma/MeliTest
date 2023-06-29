package com.jufarangoma.melitests.data

import com.jufarangoma.melitests.data.models.Attributes
import com.jufarangoma.melitests.data.models.Pictures
import com.jufarangoma.melitests.data.models.ProductDetailDTO
import com.jufarangoma.melitests.data.models.Shipping
import org.junit.Test

class MapperProductDetail {

    @Test
    fun mapToDomainEntityNull() {
        val productDetail = ProductDetailDTO()

        val mapper = productDetail.mapToEntity()

        assert(mapper.title == null)
        assert(mapper.price == null)
        assert(mapper.permalink == null)
        assert(mapper.pictures.isEmpty())
        assert(mapper.freeShipping.not())
        assert(mapper.attributes.isEmpty())
        assert(mapper.warranty == null)
    }

    @Test
    fun mapToDomainEntityNotNull() {
        val attributes = Attributes()
        val productDetail = ProductDetailDTO(
            title = "Motorola",
            price = 999.0,
            permalink = "url",
            pictures = listOf(Pictures("url1")),
            shipping = Shipping(true),
            attributes = listOf(attributes),
            warranty = "2 años"
        )

        val mapper = productDetail.mapToEntity()

        assert(mapper.title == "Motorola")
        assert(mapper.price == 999.0)
        assert(mapper.permalink == "url")
        assert(mapper.pictures.size == 1)
        assert(mapper.freeShipping)
        assert(mapper.attributes.size == 1)
        assert(mapper.warranty == "2 años")
    }

    fun mapToAttributes(){
        val attributes = Attributes("Marca", "Motorola")

        val mapper = attributes.mapToDomain()

        assert(mapper.name == "Marca")
        assert(mapper.valueName == "Motorola")
    }
}
