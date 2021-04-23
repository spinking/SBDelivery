package studio.eyesthetics.sbdelivery.data.repositories.categories

import androidx.lifecycle.LiveData
import studio.eyesthetics.sbdelivery.data.database.entities.CategoryEntity

interface ICategoryRepository {
    suspend fun loadsCategoriesFromNetwork(offset: Int, limit: Int): Int

    fun getCategories(): LiveData<List<CategoryEntity>>
}