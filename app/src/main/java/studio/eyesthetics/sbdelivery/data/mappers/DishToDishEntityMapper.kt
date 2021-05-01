package studio.eyesthetics.sbdelivery.data.mappers

import studio.eyesthetics.sbdelivery.data.database.entities.DishEntity
import studio.eyesthetics.sbdelivery.data.models.dishes.Dish

class DishToDishEntityMapper : Mapper<Dish, DishEntity> {
    override fun mapFromEntity(type: Dish): DishEntity {
        return DishEntity(
            id = type.id,
                    name = type.name,
                    description = type.description,
                    image = type.image,
                    oldPrice = type.oldPrice ?: 0,
                    price = type.price,
                    rating = type.rating,
                    likes = type.likes,
                    category = type.category,
                    commentsCount = type.commentsCount,
                    active = type.active,
                    createdAt = type.createdAt,
                    updatedAt = type.updatedAt
        )
    }

    override fun mapFromListEntity(type: List<Dish>): List<DishEntity> {
        return type.map { mapFromEntity(it) }
    }
}