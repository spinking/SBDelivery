package studio.eyesthetics.sbdelivery.data.network

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import studio.eyesthetics.sbdelivery.data.models.categories.Category

interface ICategoryApi {
    @GET("categories")
    suspend fun getCategories(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): List<Category>
}