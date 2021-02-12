package studio.eyesthetics.sbdelivery.di.modules

import dagger.Module
import dagger.Provides
import studio.eyesthetics.sbdelivery.data.database.dao.CategoriesDao
import studio.eyesthetics.sbdelivery.data.mappers.CategoryToCategoryEntityMapper
import studio.eyesthetics.sbdelivery.data.network.ICategoryApi
import studio.eyesthetics.sbdelivery.data.repositories.categories.CategoryRepository
import studio.eyesthetics.sbdelivery.data.repositories.categories.ICategoryRepository

@Module
class RepositoryModule {
    @Provides
    fun provideCategoryRepository(
        categoryApi: ICategoryApi,
        categoriesDao: CategoriesDao,
        categoriesMapper: CategoryToCategoryEntityMapper
    ) : ICategoryRepository = CategoryRepository(categoryApi, categoriesDao, categoriesMapper)
}