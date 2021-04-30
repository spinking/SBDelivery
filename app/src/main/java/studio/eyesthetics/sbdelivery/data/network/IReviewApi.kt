package studio.eyesthetics.sbdelivery.data.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import studio.eyesthetics.sbdelivery.data.models.reviews.Review

interface IReviewApi {

    @GET("reviews/{dishId}")
    suspend fun getReviews(
        @Path("dishId", encoded = false) dishId: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): List<Review>
}