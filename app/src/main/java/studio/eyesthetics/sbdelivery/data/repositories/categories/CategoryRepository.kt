package studio.eyesthetics.sbdelivery.data.repositories.categories

import studio.eyesthetics.sbdelivery.data.database.dao.CategoriesDao
import studio.eyesthetics.sbdelivery.data.mappers.CategoryToCategoryEntityMapper
import studio.eyesthetics.sbdelivery.data.models.categories.Category
import studio.eyesthetics.sbdelivery.data.network.ICategoryApi
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    private val categoryApi: ICategoryApi,
    private val categoriesDao: CategoriesDao,
    private val categoriesMapper: CategoryToCategoryEntityMapper
) : ICategoryRepository {
    override suspend fun loadsCategoriesFromNetwork(offset: Int, limit: Int): Int {
        val items = categoryApi.getCategories(offset, limit)
        if (items.isNotEmpty()) insertCategoriesToDb(items)
        return items.size
    }

    private suspend fun insertCategoriesToDb(categories: List<Category>) {
        categoriesDao.upsert(categoriesMapper.mapFromListEntity(categories).map { it })
    }
}