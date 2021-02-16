package studio.eyesthetics.sbdelivery.data.network

import retrofit2.http.Body
import retrofit2.http.PUT
import studio.eyesthetics.sbdelivery.data.models.favorites.FavoriteChangeRequest

interface IFavoriteApi {
    @PUT("favorite/change")
    suspend fun changeToFavorite(
        @Body favoriteRequest: FavoriteChangeRequest
    )
}