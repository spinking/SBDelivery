package studio.eyesthetics.sbdelivery.data.repositories.reviews

import androidx.paging.DataSource
import studio.eyesthetics.sbdelivery.data.database.entities.ReviewEntity
import studio.eyesthetics.sbdelivery.data.models.reviews.AddReviewRequest

interface IReviewRepository {
    suspend fun loadReviewsFromNetwork(dishId: String, offset: Int, limit: Int)
    suspend fun addReview(dishId: String, reviewRequest: AddReviewRequest)

    fun getReviews(dishId: String): DataSource.Factory<Int, ReviewEntity>
}