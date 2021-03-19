package studio.eyesthetics.sbdelivery.di.modules

import dagger.Module
import dagger.Provides
import studio.eyesthetics.sbdelivery.data.database.dao.CategoriesDao
import studio.eyesthetics.sbdelivery.data.database.dao.DishPersonalInfoDao
import studio.eyesthetics.sbdelivery.data.database.dao.DishesDao
import studio.eyesthetics.sbdelivery.data.database.dao.RecommendIdDao
import studio.eyesthetics.sbdelivery.data.mappers.CategoryToCategoryEntityMapper
import studio.eyesthetics.sbdelivery.data.mappers.DishToDishEntityMapper
import studio.eyesthetics.sbdelivery.data.mappers.LoginResponseToProfileMapper
import studio.eyesthetics.sbdelivery.data.network.IAuthApi
import studio.eyesthetics.sbdelivery.data.network.ICategoryApi
import studio.eyesthetics.sbdelivery.data.network.IDishesApi
import studio.eyesthetics.sbdelivery.data.network.IFavoriteApi
import studio.eyesthetics.sbdelivery.data.repositories.auth.AuthRepository
import studio.eyesthetics.sbdelivery.data.repositories.auth.IAuthRepository
import studio.eyesthetics.sbdelivery.data.repositories.categories.CategoryRepository
import studio.eyesthetics.sbdelivery.data.repositories.categories.ICategoryRepository
import studio.eyesthetics.sbdelivery.data.repositories.dishes.DishesRepository
import studio.eyesthetics.sbdelivery.data.repositories.dishes.IDishesRepository
import studio.eyesthetics.sbdelivery.data.repositories.favorite.FavoriteRepository
import studio.eyesthetics.sbdelivery.data.repositories.favorite.IFavoriteRepository
import studio.eyesthetics.sbdelivery.data.storage.Pref

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

    @Provides
    fun provideFavoriteRepository(
        favoriteApi: IFavoriteApi,
        dishPersonalInfoDao: DishPersonalInfoDao
    ) : IFavoriteRepository = FavoriteRepository(favoriteApi, dishPersonalInfoDao)

    @Provides
    fun provideAuthRepository(
        authApi: IAuthApi,
        pref: Pref,
        profileMapper: LoginResponseToProfileMapper
    ) : IAuthRepository = AuthRepository(authApi, pref, profileMapper)
}