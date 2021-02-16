package studio.eyesthetics.sbdelivery.data.repositories.dishes

import androidx.lifecycle.LiveData
import studio.eyesthetics.sbdelivery.data.database.entities.DishItem

interface IDishesRepository {
    suspend fun loadDishesFromNetwork(offset: Int, limit: Int)
    suspend fun loadRecommendIdsFromNetwork()

    fun getRecommendDishes(): LiveData<List<DishItem>>
    fun getBestDishes(): LiveData<List<DishItem>>
    fun getPopularDishes(): LiveData<List<DishItem>>
}