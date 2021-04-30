package studio.eyesthetics.sbdelivery.data.repositories.reviews

import androidx.paging.DataSource
import studio.eyesthetics.sbdelivery.data.database.dao.ReviewsDao
import studio.eyesthetics.sbdelivery.data.database.entities.ReviewEntity
import studio.eyesthetics.sbdelivery.data.mappers.ReviewToReviewEntityMapper
import studio.eyesthetics.sbdelivery.data.models.reviews.AddReviewRequest
import studio.eyesthetics.sbdelivery.data.models.reviews.Review
import studio.eyesthetics.sbdelivery.data.network.IAddReviewApi
import studio.eyesthetics.sbdelivery.data.network.IReviewApi

class ReviewRepository(
    private val reviewApi: IReviewApi,
    private val addReviewApi: IAddReviewApi,
    private val reviewsDao: ReviewsDao,
    private val reviewMapper: ReviewToReviewEntityMapper
) : IReviewRepository {
    override suspend fun loadReviewsFromNetwork(dishId: String, offset: Int, limit: Int) {
        val items = reviewApi.getReviews(dishId, offset, limit)
        if (items.isNotEmpty()) insertReviewsToDb(items)
    }

    override suspend fun addReview(dishId: String, reviewRequest: AddReviewRequest) {
        addReviewApi.sendReview(dishId, reviewRequest)
    }

    override fun getReviews(dishId: String): DataSource.Factory<Int, ReviewEntity> {
        return reviewsDao.getReviews(dishId)
    }

    private suspend fun insertReviewsToDb(reviews: List<Review>) {
        reviewsDao.upsert(reviewMapper.mapFromListEntity(reviews))
    }
}