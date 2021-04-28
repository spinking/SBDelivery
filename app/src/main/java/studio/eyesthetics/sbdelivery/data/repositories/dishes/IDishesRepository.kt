package studio.eyesthetics.sbdelivery.data.repositories.dishes

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import studio.eyesthetics.sbdelivery.data.database.entities.DishItem
import studio.eyesthetics.sbdelivery.viewmodels.SortType

interface IDishesRepository {
    suspend fun loadDishesFromNetwork(offset: Int, limit: Int)
    suspend fun loadDishesFromNetworkById(offset: Int, limit: Int, categoryId: String)
    suspend fun loadRecommendIdsFromNetwork()

    fun getRecommendDishes(): LiveData<List<DishItem>>
    fun getBestDishes(): LiveData<List<DishItem>>
    fun getPopularDishes(): LiveData<List<DishItem>>
    fun getFavoriteDishes(): LiveData<List<DishItem>>

    fun getDishes(categoryId: String, sortType: SortType): DataSource.Factory<Int, DishItem>

    fun getDishesByName(query: String): List<DishItem>
}