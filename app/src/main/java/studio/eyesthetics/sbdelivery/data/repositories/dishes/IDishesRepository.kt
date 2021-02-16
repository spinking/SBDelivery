package studio.eyesthetics.sbdelivery.data.repositories.dishes

import studio.eyesthetics.sbdelivery.data.database.entities.DishEntity

interface IDishesRepository {
    suspend fun loadDishesFromNetwork(offset: Int, limit: Int)
    suspend fun loadRecommendIdsFromNetwork()

    suspend fun getRecommendDishes(): List<DishEntity>
    suspend fun getBestDishes(): List<DishEntity>
    suspend fun getPopularDishes(): List<DishEntity>
}