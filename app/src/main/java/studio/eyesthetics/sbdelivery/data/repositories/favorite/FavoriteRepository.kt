package studio.eyesthetics.sbdelivery.data.repositories.favorite

import studio.eyesthetics.sbdelivery.data.database.dao.DishPersonalInfoDao
import studio.eyesthetics.sbdelivery.data.models.favorites.FavoriteChangeRequest
import studio.eyesthetics.sbdelivery.data.network.IFavoriteApi
import javax.inject.Inject

class FavoriteRepository @Inject constructor(
    private val favoriteApi: IFavoriteApi,
    private val dishPersonalInfoDao: DishPersonalInfoDao
) : IFavoriteRepository {

    override suspend fun changeToFavorite(favoriteCRequest: FavoriteChangeRequest) {
        //TODO add cache for favorites if user not login
        favoriteApi.changeToFavorite(favoriteCRequest)
        dishPersonalInfoDao.toggleFavoriteOrInsert(favoriteCRequest.dishId)
    }
}