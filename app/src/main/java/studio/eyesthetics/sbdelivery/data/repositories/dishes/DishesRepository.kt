package studio.eyesthetics.sbdelivery.data.repositories.dishes

import androidx.lifecycle.LiveData
import studio.eyesthetics.sbdelivery.data.database.dao.DishesDao
import studio.eyesthetics.sbdelivery.data.database.dao.RecommendIdDao
import studio.eyesthetics.sbdelivery.data.database.entities.DishItem
import studio.eyesthetics.sbdelivery.data.database.entities.RecommendIdEntity
import studio.eyesthetics.sbdelivery.data.mappers.DishToDishEntityMapper
import studio.eyesthetics.sbdelivery.data.models.dishes.Dish
import studio.eyesthetics.sbdelivery.data.network.IDishesApi
import studio.eyesthetics.sbdelivery.extensions.mutableLiveData
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

    override suspend fun loadRecommendIdsFromNetwork() {
        val ids = dishesApi.getRecommendDishesIds()
        if (ids.isEmpty())
            recommendIdDao.deleteAllIds()
        else
            recommendIdDao.insert(ids.map { RecommendIdEntity(it) })
    }

    private suspend fun insertDishesToDb(dishes: List<Dish>) {
        dishesDao.upsert(dishEntityMapper.mapFromListEntity(dishes))
    }

    override fun getRecommendDishes(): LiveData<List<DishItem>> {
        val ids = recommendIdDao.findRecommendIds()
        return if (ids.isEmpty())
            mutableLiveData()
        else
            dishesDao.findRecommendDishes(ids.map { it.id })
    }

    override fun getBestDishes(): LiveData<List<DishItem>> {
        return dishesDao.findBestDishes()
    }

    override fun getPopularDishes(): LiveData<List<DishItem>> {
        return dishesDao.findPopularDishes()
    }
}