package studio.eyesthetics.sbdelivery.data.network

import retrofit2.http.GET
import retrofit2.http.Query
import studio.eyesthetics.sbdelivery.data.models.dishes.Dish

interface IDishesApi {
    @GET("dishes")
    suspend fun getDishes(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): List<Dish>

    @GET("dishes")
    suspend fun getDishes(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
        @Query("id") categoryId: String
    ): List<Dish>

    @GET("main/recommend")
    suspend fun getRecommendDishesIds(): List<String>
}