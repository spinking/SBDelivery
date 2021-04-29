package studio.eyesthetics.sbdelivery.data.mappers

import studio.eyesthetics.sbdelivery.data.database.entities.ReviewEntity
import studio.eyesthetics.sbdelivery.data.models.reviews.Review

class ReviewToReviewEntityMapper : Mapper<Review, ReviewEntity> {

    override fun mapFromEntity(type: Review): ReviewEntity {
        return ReviewEntity(
            id = type.id,
            dishId = type.dishId,
            author = type.author,
            date = type.date,
            rating = type.rating,
            text = type.text,
            active = type.active,
            createdAt = type.createdAt,
            updatedAt = type.updatedAt
        )
    }

    override fun mapFromListEntity(type: List<Review>): List<ReviewEntity> {
        return type.map { mapFromEntity(it) }
    }
}