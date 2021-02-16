package studio.eyesthetics.sbdelivery.di.modules

import dagger.Module
import dagger.Provides
import studio.eyesthetics.sbdelivery.data.database.dao.CategoriesDao
import studio.eyesthetics.sbdelivery.data.database.dao.DishesDao
import studio.eyesthetics.sbdelivery.data.database.dao.RecommendIdDao
import studio.eyesthetics.sbdelivery.data.mappers.CategoryToCategoryEntityMapper
import studio.eyesthetics.sbdelivery.data.mappers.DishToDishEntityMapper
import studio.eyesthetics.sbdelivery.data.network.ICategoryApi
import studio.eyesthetics.sbdelivery.data.network.IDishesApi
import studio.eyesthetics.sbdelivery.data.repositories.categories.CategoryRepository
import studio.eyesthetics.sbdelivery.data.repositories.categories.ICategoryRepository
import studio.eyesthetics.sbdelivery.data.repositories.dishes.DishesRepository
import studio.eyesthetics.sbdelivery.data.repositories.dishes.IDishesRepository

@Module
class RepositoryModule {
    @Provides
    fun provideCategoryRepository(
        categoryApi: ICategoryApi,
        categoriesDao: CategoriesDao,
        categoriesMapper: CategoryToCategoryEntityMapper
    ) : ICategoryRepository = CategoryRepository(categoryApi, categoriesDao, categoriesMapper)

    @Provides
    fun provideDishesRepository(
        dishesApi: IDishesApi,
        dishesDao: DishesDao,
        dishesEntityMapper: DishToDishEntityMapper,
        recommendIdDao: RecommendIdDao
    ) : IDishesRepository = DishesRepository(dishesApi, dishesDao, dishesEntityMapper, recommendIdDao)
}