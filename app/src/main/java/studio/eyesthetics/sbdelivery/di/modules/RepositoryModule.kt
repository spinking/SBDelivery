package studio.eyesthetics.sbdelivery.di.modules

import dagger.Module
import dagger.Provides
import studio.eyesthetics.sbdelivery.data.database.dao.*
import studio.eyesthetics.sbdelivery.data.mappers.*
import studio.eyesthetics.sbdelivery.data.network.*
import studio.eyesthetics.sbdelivery.data.repositories.auth.AuthRepository
import studio.eyesthetics.sbdelivery.data.repositories.auth.IAuthRepository
import studio.eyesthetics.sbdelivery.data.repositories.basket.BasketRepository
import studio.eyesthetics.sbdelivery.data.repositories.basket.IBasketRepository
import studio.eyesthetics.sbdelivery.data.repositories.categories.CategoryRepository
import studio.eyesthetics.sbdelivery.data.repositories.categories.ICategoryRepository
import studio.eyesthetics.sbdelivery.data.repositories.dishes.DishesRepository
import studio.eyesthetics.sbdelivery.data.repositories.dishes.IDishesRepository
import studio.eyesthetics.sbdelivery.data.repositories.favorite.FavoriteRepository
import studio.eyesthetics.sbdelivery.data.repositories.favorite.IFavoriteRepository
import studio.eyesthetics.sbdelivery.data.repositories.reviews.IReviewRepository
import studio.eyesthetics.sbdelivery.data.repositories.reviews.ReviewRepository
import studio.eyesthetics.sbdelivery.data.repositories.suggestion.ISuggestionRepository
import studio.eyesthetics.sbdelivery.data.repositories.suggestion.SuggestionRepository
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

    @Provides
    fun provideSuggestionRepository(
        suggestionsDao: SuggestionsDao
    ) : ISuggestionRepository = SuggestionRepository(suggestionsDao)

    @Provides
    fun provideReviewRepository(
        reviewApi: IReviewApi,
        addReviewApi: IAddReviewApi,
        reviewsDao: ReviewsDao,
        reviewMapper: ReviewToReviewEntityMapper
    ) : IReviewRepository = ReviewRepository(reviewApi, addReviewApi, reviewsDao, reviewMapper)

    @Provides
    fun provideBasketRepository(
        basketApi: IBasketApi,
        basketDao: BasketDao,
        basketItemDao: BasketItemDao,
        basketMapper: BasketResponseToBasketEntity,
        basketItemMapper: BasketItemToBasketItemEntity,
        basketShortMapper: BasketEntityToBasketShortMapper
    ) : IBasketRepository = BasketRepository(basketApi, basketDao, basketItemDao, basketMapper, basketItemMapper, basketShortMapper)
}