package studio.eyesthetics.sbdelivery.di.modules

import dagger.Module
import dagger.Provides
import studio.eyesthetics.sbdelivery.data.mappers.*

@Module
class MapperModule {
    @Provides
    fun provideCategoriesMapper(): CategoryToCategoryEntityMapper = CategoryToCategoryEntityMapper()

    @Provides
    fun provideDishesMapper(): DishToDishEntityMapper = DishToDishEntityMapper()

    @Provides
    fun provideProfileMapper(): ProfileResponseToProfileMapper = ProfileResponseToProfileMapper()

    @Provides
    fun provideLoginResponseToProfileMapper(): LoginResponseToProfileMapper = LoginResponseToProfileMapper()

    @Provides
    fun provideReviewMapper(): ReviewToReviewEntityMapper = ReviewToReviewEntityMapper()

    @Provides
    fun provideBasketItemMapper(): BasketItemToBasketItemEntity = BasketItemToBasketItemEntity()

    @Provides
    fun provideBasketMapper(): BasketResponseToBasketEntity = BasketResponseToBasketEntity()

    @Provides
    fun provideBasketShortMapper(): BasketEntityToBasketShortMapper = BasketEntityToBasketShortMapper()
}