package studio.eyesthetics.sbdelivery.data.mappers

import studio.eyesthetics.sbdelivery.data.database.entities.BasketItemEntity
import studio.eyesthetics.sbdelivery.data.models.basket.BasketItem

class BasketItemToBasketItemEntity : Mapper<BasketItem, BasketItemEntity> {
    override fun mapFromEntity(type: BasketItem): BasketItemEntity {
        return BasketItemEntity(
            id = type.id,
            basketId = 1,
            amount = type.amount,
            price = type.price
        )
    }

    override fun mapFromListEntity(type: List<BasketItem>): List<BasketItemEntity> {
        return type.map { mapFromEntity(it) }
    }
}