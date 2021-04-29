package studio.eyesthetics.sbdelivery.data.network

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path
import studio.eyesthetics.sbdelivery.data.models.reviews.AddReviewRequest

interface IAddReviewApi {

    @POST("reviews")
    fun sendReview(
        @Path("dishId") dishId: String,
        @Body reviewRequest: AddReviewRequest
    )
}