package studio.eyesthetics.sbdelivery.data.repositories.dishes

import studio.eyesthetics.sbdelivery.data.database.dao.DishesDao
import studio.eyesthetics.sbdelivery.data.database.dao.RecommendIdDao
import studio.eyesthetics.sbdelivery.data.database.entities.DishEntity
import studio.eyesthetics.sbdelivery.data.database.entities.RecommendIdEntity
import studio.eyesthetics.sbdelivery.data.mappers.DishToDishEntityMapper
import studio.eyesthetics.sbdelivery.data.models.dishes.Dish
import studio.eyesthetics.sbdelivery.data.network.IDishesApi
import javax.inject.Inject

class DishesRepository @Inject constructor(
    private val dishesApi: IDishesApi,
    private val dishesDao: DishesDao,
    private val dishEntityMapper: DishToDishEntityMapper,
    private val recommendIdDao: RecommendIdDao
) : IDishesRepository {
    override suspend fun loadDishesFromNetwork(offset: Int, limit: Int) {
        val items = dishesApi.getDishes(offset, limit)
        if (items.isNotEmpty()) insertDishesToDb(items)
    }

    private suspend fun insertDishesToDb(dishes: List<Dish>) {
        dishesDao.upsert(dishEntityMapper.mapFromListEntity(dishes))
    }

    override suspend fun loadRecommendIdsFromNetwork() {
        val ids = dishesApi.getRecommendDishesIds()
        if (ids.isEmpty())
            recommendIdDao.deleteAllIds()
        else
            recommendIdDao.insert(ids.map { RecommendIdEntity(it) })
    }

    override suspend fun getRecommendDishes(): List<DishEntity> {
        val ids = recommendIdDao.findRecommendIds()
        return if (ids.isEmpty())
            emptyList()
        else
            dishesDao.findRecommendDishes(ids.map { it.id }).shuffled()
    }

    override suspend fun getBestDishes(): List<DishEntity> {
        return dishesDao.findBestDishes().shuffled()
    }

    override suspend fun getPopularDishes(): List<DishEntity> {
        return dishesDao.findPopularDishes().shuffled()
    }
}