package studio.eyesthetics.sbdelivery.di.modules

import dagger.Module
import dagger.Provides
import studio.eyesthetics.sbdelivery.data.mappers.CategoryToCategoryEntityMapper
import studio.eyesthetics.sbdelivery.data.mappers.DishToDishEntityMapper

@Module
class MapperModule {
    @Provides
    fun provideCategoriesMapper(): CategoryToCategoryEntityMapper = CategoryToCategoryEntityMapper()

    @Provides
    fun provideDishesMapper(): DishToDishEntityMapper = DishToDishEntityMapper()
}