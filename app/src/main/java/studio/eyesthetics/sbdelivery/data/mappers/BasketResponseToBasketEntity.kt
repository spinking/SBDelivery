package studio.eyesthetics.sbdelivery.data.mappers

import studio.eyesthetics.sbdelivery.data.database.entities.BasketEntity
import studio.eyesthetics.sbdelivery.data.models.basket.BasketResponse

class BasketResponseToBasketEntity : Mapper<BasketResponse, BasketEntity> {
    override fun mapFromEntity(type: BasketResponse): BasketEntity {
        return BasketEntity(
            id = 1L,
            promocode = type.promocode,
            promotext = type.promotext,
            total = type.total
        )
    }

    override fun mapFromListEntity(type: List<BasketResponse>): List<BasketEntity> {
        return type.map { mapFromEntity(it) }
    }
}