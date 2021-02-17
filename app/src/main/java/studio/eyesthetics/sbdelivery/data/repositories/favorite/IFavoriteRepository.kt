package studio.eyesthetics.sbdelivery.data.repositories.favorite

import studio.eyesthetics.sbdelivery.data.models.favorites.FavoriteChangeRequest

interface IFavoriteRepository {
    suspend fun changeToFavorite(favoriteCRequest: FavoriteChangeRequest)
}