package studio.eyesthetics.sbdelivery.data.repositories.categories

import androidx.lifecycle.LiveData
import studio.eyesthetics.sbdelivery.data.database.dao.CategoriesDao
import studio.eyesthetics.sbdelivery.data.database.entities.CategoryEntity
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

    override fun getCategories(): LiveData<List<CategoryEntity>> {
        return categoriesDao.getCategories()
    }

    override fun getCategoriesByParentId(categoryId: String): List<CategoryEntity> {
        return categoriesDao.getCategoriesByParentId(categoryId)
    }

    override fun getCategoriesByName(query: String): List<CategoryEntity> {
        return categoriesDao.getCategoriesByName(query)
    }

    private suspend fun insertCategoriesToDb(categories: List<Category>) {
        categoriesDao.upsert(categoriesMapper.mapFromListEntity(categories))
    }
}