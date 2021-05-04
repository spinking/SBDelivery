package studio.eyesthetics.sbdelivery.data.network

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import studio.eyesthetics.sbdelivery.data.models.orders.CancelOrderRequest
import studio.eyesthetics.sbdelivery.data.models.orders.CreateOrderRequest
import studio.eyesthetics.sbdelivery.data.models.orders.OrderResponse
import studio.eyesthetics.sbdelivery.data.models.orders.Status

interface IOrderApi {
    @POST("orders/new")
    suspend fun createOrder(
        @Body orderRequest: CreateOrderRequest
    ): OrderResponse

    @GET("orders")
    suspend fun getOrders(offset: Int, limit: Int): List<OrderResponse>

    @GET("orders/statuses")
    suspend fun getOrderStatuses(): List<Status>

    @PUT("orders/cancel")
    suspend fun cancelOrder(
        @Body cancelOrderRequest: CancelOrderRequest
    ): OrderResponse
}