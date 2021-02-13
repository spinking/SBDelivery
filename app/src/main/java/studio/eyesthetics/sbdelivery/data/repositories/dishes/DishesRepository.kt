package studio.eyesthetics.sbdelivery.data.repositories.dishes

import studio.eyesthetics.sbdelivery.data.database.dao.DishesDao
import studio.eyesthetics.sbdelivery.data.mappers.DishToDishEntityMapper
import studio.eyesthetics.sbdelivery.data.models.dishes.Dish
import studio.eyesthetics.sbdelivery.data.network.IDishesApi
import javax.inject.Inject

class DishesRepository @Inject constructor(
    private val dishesApi: IDishesApi,
    private val dishesDao: DishesDao,
    private val dishEntityMapper: DishToDishEntityMapper
) : IDishesRepository {
    override suspend fun loadDishesFromNetwork(offset: Int, limit: Int) {
        val items = dishesApi.getDishes(offset, limit)
        if (items.isNotEmpty()) insertDishesToDb(items)
    }

    private suspend fun insertDishesToDb(dishes: List<Dish>) {
        dishesDao.upsert(dishEntityMapper.mapFromListEntity(dishes))
    }
}