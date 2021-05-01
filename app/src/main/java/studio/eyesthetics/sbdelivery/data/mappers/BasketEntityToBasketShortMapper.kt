package studio.eyesthetics.sbdelivery.data.mappers

import studio.eyesthetics.sbdelivery.data.database.entities.BasketItemEntity
import studio.eyesthetics.sbdelivery.data.models.basket.BasketItemShort

class BasketEntityToBasketShortMapper : Mapper<BasketItemEntity, BasketItemShort> {
    override fun mapFromEntity(type: BasketItemEntity): BasketItemShort {
        return BasketItemShort(
            id = type.id,
            amount = type.amount
        )
    }

    override fun mapFromListEntity(type: List<BasketItemEntity>): List<BasketItemShort> {
        return type.map { mapFromEntity(it) }
    }
}