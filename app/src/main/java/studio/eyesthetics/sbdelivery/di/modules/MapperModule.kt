package studio.eyesthetics.sbdelivery.di.modules

import dagger.Module
import dagger.Provides
import studio.eyesthetics.sbdelivery.data.mappers.CategoryToCategoryEntityMapper

@Module
class MapperModule {
    @Provides
    fun provideCategoriesMapper(): CategoryToCategoryEntityMapper = CategoryToCategoryEntityMapper()
}