package studio.eyesthetics.sbdelivery.data.network

import retrofit2.http.GET
import retrofit2.http.PUT
import studio.eyesthetics.sbdelivery.data.models.basket.BasketRequest
import studio.eyesthetics.sbdelivery.data.models.basket.BasketResponse

interface IBasketApi {

    @GET("cart")
    suspend fun getBasket(): BasketResponse

    @PUT("cart")
    suspend fun updateBasket(basketRequest: BasketRequest): BasketResponse
}